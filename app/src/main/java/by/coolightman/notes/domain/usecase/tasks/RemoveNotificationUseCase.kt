package by.coolightman.notes.domain.usecase.tasks

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import by.coolightman.notes.broadcastReceiver.NotificationReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoveNotificationUseCase @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    operator fun invoke(taskId: Long) {
        val intent = Intent(appContext, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            taskId.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
    }
}