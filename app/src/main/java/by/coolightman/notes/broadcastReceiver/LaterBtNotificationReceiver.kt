package by.coolightman.notes.broadcastReceiver

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.domain.model.RepeatType
import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA
import by.coolightman.notes.util.TASK_ID_EXTRA
import by.coolightman.notes.util.convertToCalendar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LaterBtNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var alarmManager: AlarmManager

    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(NOTIFICATION_ID_EXTRA, -1)
        val taskId = intent.getLongExtra(TASK_ID_EXTRA, -1L)
        if (notificationId != -1 && taskId != -1L) {

            notificationManager.cancel(notificationId)

            CoroutineScope(Dispatchers.Main).launch {
                val timePlus10Min = System.currentTimeMillis() + 10 * 60 * 1000
                val laterNotificationId = async {
                    notificationRepository.create(
                        Notification(
                            time = timePlus10Min.convertToCalendar(),
                            taskId = taskId,
                            repeatType = RepeatType.NO
                        )
                    )
                }
                val newNotificationId = laterNotificationId.await()
                val laterBtIntent = Intent(context, NotificationReceiver::class.java).apply {
                    putExtra(NOTIFICATION_ID_EXTRA, newNotificationId)
                }
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    newNotificationId,
                    laterBtIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, timePlus10Min, pendingIntent
                )
            }
        }
    }
}