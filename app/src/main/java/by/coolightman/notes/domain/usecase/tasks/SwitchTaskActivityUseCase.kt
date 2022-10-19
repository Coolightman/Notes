package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class SwitchTaskActivityUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val removeNotificationUseCase: RemoveNotificationUseCase
) {
    suspend operator fun invoke(taskId: Long) {
        val task = repository.getTask(taskId)
        val switched = task.copy(
            isActive = !task.isActive,
            isHasNotification = false
        )
        updateNotification(switched)
        repository.update(switched)
    }

    private fun updateNotification(task: Task) {
        if (!task.isActive) {
            removeNotificationUseCase(task.id)
        }
    }
}
