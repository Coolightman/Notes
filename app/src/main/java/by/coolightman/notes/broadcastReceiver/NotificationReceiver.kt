package by.coolightman.notes.broadcastReceiver

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import by.coolightman.notes.R
import by.coolightman.notes.ui.MainActivity
import by.coolightman.notes.util.NOTIFICATION_CHANNEL_DESCRIPTION
import by.coolightman.notes.util.NOTIFICATION_CHANNEL_ID
import by.coolightman.notes.util.NOTIFICATION_CHANNEL_NAME
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA
import by.coolightman.notes.util.NOTIFICATION_TEXT_EXTRA
import by.coolightman.notes.util.NOTIFICATION_TIME

class NotificationReceiver : BroadcastReceiver() {

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent.getIntExtra(NOTIFICATION_ID_EXTRA, 0)
        val notificationTitle =
            context.resources.getString(R.string.task_notification_title) + " " + context.getString(
                R.string.task_for_time
            ) + " " + intent.getStringExtra(NOTIFICATION_TIME)
        val notificationText = intent.getStringExtra(NOTIFICATION_TEXT_EXTRA)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = NOTIFICATION_CHANNEL_DESCRIPTION
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val launchAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val launchAppPendingIntent =
            PendingIntent.getActivity(context, 0, launchAppIntent, PendingIntent.FLAG_IMMUTABLE)

        val okBtIntent = Intent(context, CancelNotificationReceiver::class.java).apply {
            putExtra(NOTIFICATION_ID_EXTRA, notificationId)
        }
        val okBtPendingIntent = PendingIntent.getBroadcast(context, 0, okBtIntent, 0)

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentIntent(launchAppPendingIntent)
            .addAction(
                R.drawable.ic_round_done_24,
                context.resources.getString(R.string.okay),
                okBtPendingIntent
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
