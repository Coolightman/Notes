package by.coolightman.notes.domain.repository

interface NotificationRepository {

    fun createNotification(id: Int, text: String, time: Long)

    fun removeNotification(id: Int)
}
