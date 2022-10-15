package by.coolightman.notes.broadcastReceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import by.coolightman.notes.R
import by.coolightman.notes.ui.MainActivity
import by.coolightman.notes.util.*

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val launchAppIntent = Intent(context, MainActivity::class.java)
        val launchAppPendingIntent =
            PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(
                context.resources.getString(R.string.task_notification_title) + " " +
                        context.getString(R.string.task_for_time) + " " +
                        intent.getStringExtra(NOTIFICATION_TIME)
            )
            .setContentText(intent.getStringExtra(NOTIFICATION_TEXT_EXTRA))
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(launchAppPendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationId = intent.getIntExtra(NOTIFICATION_ID_EXTRA, 0)

        notificationManager.notify(notificationId, notification)
    }
}