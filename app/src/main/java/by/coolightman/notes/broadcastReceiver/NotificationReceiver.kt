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
import by.coolightman.notes.util.NOTIFICATION_TIME_EXTRA
import by.coolightman.notes.util.toFormattedTime

class NotificationReceiver : BroadcastReceiver() {

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = intent.getIntExtra(NOTIFICATION_ID_EXTRA, 0)
        val notificationTime = intent.getLongExtra(NOTIFICATION_TIME_EXTRA, 0L)
        val notificationTitle =
            context.resources.getString(R.string.task_notification_title) + " " + context.getString(
                R.string.task_for_time
            ) + " " + notificationTime.toFormattedTime()
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

        val okBtIntent = Intent(context, OkBtNotificationReceiver::class.java).apply {
            putExtra(NOTIFICATION_ID_EXTRA, notificationId)
        }
        val okBtPendingIntent =
            PendingIntent.getBroadcast(
                context,
                notificationId,
                okBtIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val laterBtIntent = Intent(context, LaterBtNotificationReceiver::class.java).apply {
            putExtra(NOTIFICATION_ID_EXTRA, notificationId)
            putExtra(NOTIFICATION_TIME_EXTRA, notificationTime)
            putExtra(NOTIFICATION_TEXT_EXTRA, notificationText)
        }
        val laterBtPendingIntent =
            PendingIntent.getBroadcast(
                context,
                notificationId,
                laterBtIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

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
            ).addAction(
                R.drawable.ic_round_access_time_24,
                context.resources.getString(R.string.remind_in_10_minutes),
                laterBtPendingIntent
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
