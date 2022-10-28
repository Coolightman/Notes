package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.domain.repository.NotificationRepository
import javax.inject.Inject

class CreateNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(notification: Notification) = repository.create(notification)
}
