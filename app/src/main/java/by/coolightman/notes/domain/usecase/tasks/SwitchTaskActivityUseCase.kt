package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class SwitchTaskActivityUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(taskId: Long) {
        val task = repository.getTask(taskId)
        val switched = task.copy(
            isActive = !task.isActive
        )
        updateNotifications(switched)
        repository.update(switched)
    }

    private suspend fun updateNotifications(task: Task) {
        if (!task.isActive) {
            task.notifications.forEach {
                notificationRepository.delete(it.id)
            }
        }
    }
}
