package cl.grupo11.mmconecta.repository

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContentNotifierTest {
    @Test fun createsChannelAndRespectsCurrentSystemPermission() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notifier = SystemContentNotifier(context)
        val channel = manager.getNotificationChannel(SystemContentNotifier.CHANNEL_ID)
        assertNotNull(channel)
        assertEquals("Contenido nuevo", channel.name.toString())

        // No cambia los permisos ni las preferencias de notificación del usuario.
        val permissionDenied = Build.VERSION.SDK_INT >= 33 &&
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        if (permissionDenied) {
            assertThrows(SecurityException::class.java) {
                notifier.notifyNewContent("Prueba", "Contenido sintético")
            }
        } else if (!manager.areNotificationsEnabled() || channel.importance == NotificationManager.IMPORTANCE_NONE) {
            assertThrows(NotificationsBlockedException::class.java) {
                notifier.notifyNewContent("Prueba", "Contenido sintético")
            }
        }
        // La entrega visible se prueba manualmente; no se publica una notificación aquí.
    }
}
