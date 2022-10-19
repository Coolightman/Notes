package by.coolightman.notes.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import by.coolightman.notes.broadcastReceiver.NotificationReceiver
import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA
import by.coolightman.notes.util.NOTIFICATION_TEXT_EXTRA
import by.coolightman.notes.util.NOTIFICATION_TIME
import by.coolightman.notes.util.toFormattedTime
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val alarmManager: AlarmManager
) : NotificationRepository {

    override fun createNotification(id: Int, text: String, time: Long) {
        val intent = Intent(appContext, NotificationReceiver::class.java)
        intent.putExtra(NOTIFICATION_ID_EXTRA, id)
        intent.putExtra(NOTIFICATION_TEXT_EXTRA, text)
        intent.putExtra(NOTIFICATION_TIME, time.toFormattedTime())

        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, time, pendingIntent
        )
    }

    override fun removeNotification(id: Int) {
        val intent = Intent(appContext, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.cancel(pendingIntent)
    }
}
