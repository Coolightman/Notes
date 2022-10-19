package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val createNotificationUseCase: CreateNotificationUseCase,
    private val removeNotificationUseCase: RemoveNotificationUseCase
) {
    suspend operator fun invoke(task: Task) {
        repository.update(task)
        if (task.isHasNotification) {
            createNotificationUseCase(task.id, task.text, task.notificationTime)
        } else {
            removeNotificationUseCase(task.id)
        }
    }
}
