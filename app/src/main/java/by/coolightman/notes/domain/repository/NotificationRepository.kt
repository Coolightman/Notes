package by.coolightman.notes.domain.repository

import by.coolightman.notes.domain.model.Notification

interface NotificationRepository {

    suspend fun create(notification: Notification): Int

    suspend fun getNotification(id: Int): Notification

    suspend fun getAll(): List<Notification>

    suspend fun update(notification: Notification)

    suspend fun updateList(list: List<Notification>)

    suspend fun delete(id: Int)

    suspend fun deleteAllByTask(taskId: Long)
}
