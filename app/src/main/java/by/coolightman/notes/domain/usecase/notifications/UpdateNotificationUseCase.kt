package by.coolightman.notes.domain.usecase.notifications

import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.domain.repository.NotificationRepository
import javax.inject.Inject

class UpdateNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(notification: Notification) = repository.update(notification)
}
