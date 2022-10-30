package by.coolightman.notes.domain.usecase.notifications

import by.coolightman.notes.domain.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}
