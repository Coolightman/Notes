package by.coolightman.notes.broadcastReceiver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.domain.model.RepeatType
import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.domain.repository.TaskRepository
import by.coolightman.notes.ui.MainActivity
import by.coolightman.notes.util.NOTIFICATION_CHANNEL_DESCRIPTION
import by.coolightman.notes.util.NOTIFICATION_CHANNEL_ID
import by.coolightman.notes.util.NOTIFICATION_CHANNEL_NAME
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA
import by.coolightman.notes.util.TASK_ID_EXTRA
import by.coolightman.notes.util.toFormattedTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var taskRepository: TaskRepository

    @Inject
    lateinit var notificationRepository: NotificationRepository

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        CoroutineScope(Main).launch {
            val notificationId = intent.getIntExtra(NOTIFICATION_ID_EXTRA, -1)
            if (notificationId != -1) {
                val notification = notificationRepository.getNotification(notificationId)
                val task = taskRepository.getTask(notification.taskId)
                showNotification(context, notification, task.text)
                updateNotificationsDb(notification)
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(context: Context, notification: Notification, text: String) {
        val notificationTitle =
            context.resources.getString(R.string.task_notification_title) +
                    " " + context.getString(R.string.task_for_time) + " " +
                    notification.time.timeInMillis.toFormattedTime()

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
            putExtra(TASK_ID_EXTRA, notification.taskId)
        }
        val okBtPendingIntent =
            PendingIntent.getBroadcast(
                context,
                notification.taskId.toInt(),
                okBtIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val laterBtIntent = Intent(context, LaterBtNotificationReceiver::class.java).apply {
            putExtra(NOTIFICATION_ID_EXTRA, notification.id)
            putExtra(TASK_ID_EXTRA, notification.taskId)
        }
        val laterBtPendingIntent =
            PendingIntent.getBroadcast(
                context,
                notification.id,
                laterBtIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val showingNotification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(notificationTitle)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
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

        notificationManager.notify(notification.taskId.toInt(), showingNotification)
    }

    private suspend fun updateNotificationsDb(notification: Notification) {
        when (notification.repeatType) {
            RepeatType.NO -> notificationRepository.delete(notification.id)
            else -> {
                val updatedNotification = updateByRepeatType(notification)
                notificationRepository.update(updatedNotification)
            }
        }
    }

    private fun updateByRepeatType(notification: Notification): Notification {
        val time = notification.time
        val updatedTime = when (notification.repeatType) {
            RepeatType.DAY -> time.apply {
                add(Calendar.DAY_OF_MONTH, 1)
            }
            RepeatType.WEEK -> time.apply {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
            RepeatType.MONTH -> time.apply {
                add(Calendar.MONTH, 1)
            }
            RepeatType.YEAR -> time.apply {
                add(Calendar.YEAR, 1)
            }
            else -> throw RuntimeException("Wrong RepeatType!")
        }
        return notification.copy(time = updatedTime)
    }
}
