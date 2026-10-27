package cl.grupo11.mmconecta.repository

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import cl.grupo11.mmconecta.MainActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotificationsBlockedException : IllegalStateException()

interface ContentNotifier {
    fun notifyNewContent(title: String, message: String)
}

class SystemContentNotifier(private val context: Context) : ContentNotifier {

    companion object {
        const val CHANNEL_ID = "contenido_nuevo"
        private const val NOTIFICATION_ID = 1001
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Contenido nuevo",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Avisos de nuevos contenidos disponibles en MMConecta"
            }
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun notifyNewContent(title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) throw SecurityException("Permiso de notificaciones no concedido")
        }
        val notificationManager = NotificationManagerCompat.from(context)
        val systemManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = systemManager.getNotificationChannel(CHANNEL_ID)
        if (!notificationManager.areNotificationsEnabled() ||
            channel?.importance == NotificationManager.IMPORTANCE_NONE) {
            throw NotificationsBlockedException()
        }
        val openApp = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(openApp)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
