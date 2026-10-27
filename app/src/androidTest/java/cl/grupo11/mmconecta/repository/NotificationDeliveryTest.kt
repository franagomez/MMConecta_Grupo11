package cl.grupo11.mmconecta.repository

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.SystemClock
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class NotificationDeliveryTest {
    @get:Rule val permission: GrantPermissionRule = GrantPermissionRule.grant(
        *if (Build.VERSION.SDK_INT >= 33) arrayOf(Manifest.permission.POST_NOTIFICATIONS) else emptyArray()
    )

    @Test fun publishesNotificationWithActionToOpenApp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifier = SystemContentNotifier(context)
        try {
            notifier.notifyNewContent("Prueba de apertura", "Contenido de prueba")
            val deadline = SystemClock.elapsedRealtime() + 5_000
            var delivered = manager.activeNotifications.firstOrNull {
                it.notification.channelId == SystemContentNotifier.CHANNEL_ID
            }
            while (delivered == null && SystemClock.elapsedRealtime() < deadline) {
                SystemClock.sleep(50)
                delivered = manager.activeNotifications.firstOrNull {
                    it.notification.channelId == SystemContentNotifier.CHANNEL_ID
                }
            }
            val posted = requireNotNull(delivered) {
                "Android no publicó la notificación en cinco segundos"
            }.notification
            assertEquals("Prueba de apertura", posted.extras.getString(Notification.EXTRA_TITLE))
            assertEquals("Contenido de prueba", posted.extras.getString(Notification.EXTRA_TEXT))
            assertNotNull(posted.contentIntent)
            assertEquals(context.packageName, posted.contentIntent.creatorPackage)
            assertTrue(posted.flags and Notification.FLAG_AUTO_CANCEL != 0)
        } finally {
            manager.activeNotifications.filter {
                it.notification.channelId == SystemContentNotifier.CHANNEL_ID
            }.forEach { manager.cancel(it.tag, it.id) }
        }
    }
}
