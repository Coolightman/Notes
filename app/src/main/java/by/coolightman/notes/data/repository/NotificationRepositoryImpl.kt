package by.coolightman.notes.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import by.coolightman.notes.broadcastReceiver.NotificationReceiver
import by.coolightman.notes.data.local.dao.NotificationDao
import by.coolightman.notes.data.mappers.toNotificationDb
import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.util.NOTIFICATION_ID_EXTRA
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val alarmManager: AlarmManager,
    private val notificationDao: NotificationDao
) : NotificationRepository {

    override suspend fun create(notification: Notification) {
        createNotification(notification)
        notificationDao.insert(notification.toNotificationDb())
    }

    override suspend fun update(notification: Notification) {
        updateNotification(notification)
        notificationDao.update(notification.toNotificationDb())
    }

    override suspend fun updateList(list: List<Notification>) {
        list.forEach { updateNotification(it) }
        notificationDao.updateList(list.map { it.toNotificationDb() })
    }

    override suspend fun delete(id: Int) {
        removeNotification(id)
        notificationDao.delete(id)
    }

    override suspend fun deleteAllByTask(taskId: Long) {
        notificationDao.getAllByTask(taskId).forEach {
            removeNotification(it.id)
        }
        notificationDao.deleteByTask(taskId)
    }

    private fun createNotification(notification: Notification) {
        val intent = Intent(appContext, NotificationReceiver::class.java).apply {
            putExtra(NOTIFICATION_ID_EXTRA, notification.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            appContext,
            notification.id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, notification.time.timeInMillis, pendingIntent
        )
    }

    private fun updateNotification(notification: Notification) {
        removeNotification(notification.id)
        createNotification(notification)
    }

    private fun removeNotification(id: Int) {
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
