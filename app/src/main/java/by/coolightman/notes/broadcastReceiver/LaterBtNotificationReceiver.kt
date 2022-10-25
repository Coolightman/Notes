package by.coolightman.notes.broadcastReceiver

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA
import by.coolightman.notes.util.NOTIFICATION_TEXT_EXTRA
import by.coolightman.notes.util.NOTIFICATION_TIME_EXTRA

class LaterBtNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTIFICATION_ID_EXTRA, 0)
        val notificationTime = intent.getLongExtra(NOTIFICATION_TIME_EXTRA, 0L)
        val notificationText = intent.getStringExtra(NOTIFICATION_TEXT_EXTRA)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val laterBtIntent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra(NOTIFICATION_ID_EXTRA, notificationId)
            putExtra(NOTIFICATION_TIME_EXTRA, notificationTime)
            putExtra(NOTIFICATION_TEXT_EXTRA, notificationText)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            laterBtIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val timePlus10Min = System.currentTimeMillis() + 10 * 60 * 1000
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, timePlus10Min, pendingIntent
        )
    }
}