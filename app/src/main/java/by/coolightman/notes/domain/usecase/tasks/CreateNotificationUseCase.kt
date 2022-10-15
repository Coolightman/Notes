package by.coolightman.notes.domain.usecase.tasks

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import by.coolightman.notes.broadcastReceiver.NotificationReceiver
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA
import by.coolightman.notes.util.NOTIFICATION_TEXT_EXTRA
import by.coolightman.notes.util.NOTIFICATION_TIME
import by.coolightman.notes.util.toFormattedTime
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject

class CreateNotificationUseCase @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    operator fun invoke(taskId: Int, taskText: String, calendar: Calendar){
        val intent = Intent(appContext, NotificationReceiver::class.java)
        intent.putExtra(NOTIFICATION_TEXT_EXTRA, taskText)
        intent.putExtra(NOTIFICATION_ID_EXTRA, taskId)
        intent.putExtra(NOTIFICATION_TIME, calendar.timeInMillis.toFormattedTime())

        val pendingIntent = PendingIntent.getBroadcast(
            appContext, taskId, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val scheduledTime = calendar.timeInMillis

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, scheduledTime, pendingIntent
        )
    }
}