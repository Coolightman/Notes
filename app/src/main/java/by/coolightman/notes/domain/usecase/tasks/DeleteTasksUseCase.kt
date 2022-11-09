package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTasksUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(list: List<Task>) {
        list.forEach {
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
