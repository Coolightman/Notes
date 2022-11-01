package by.coolightman.notes.domain.usecase.notifications

import by.coolightman.notes.domain.repository.NotificationRepository
import javax.inject.Inject

class ReassignNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(){
        val list = repository.getAll()
        repository.updateList(list)
    }
}