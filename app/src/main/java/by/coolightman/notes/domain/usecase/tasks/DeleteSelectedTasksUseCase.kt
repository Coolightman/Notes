package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteSelectedTasksUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke() {
        val inactiveList = repository.getAll().first().filter { it.isSelected }
        inactiveList.forEach {
            deleteNotifications(it)
            repository.delete(it.id)
        }
    }

    private suspend fun deleteNotifications(it: Task) {
        it.notifications.forEach { notification ->
            notificationRepository.delete(notification.id)
        }
    }
}
