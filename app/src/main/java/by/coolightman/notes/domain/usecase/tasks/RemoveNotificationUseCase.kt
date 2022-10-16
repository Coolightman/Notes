package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.NotificationRepository
import javax.inject.Inject

class RemoveNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(id: Long) {
        repository.removeNotification(id.toInt())
    }
}