package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val createNotificationUseCase: CreateNotificationUseCase
) {
    suspend operator fun invoke(task: Task) {
        val taskId = repository.insert(task)
        if (task.isHasNotification) {
            createNotificationUseCase(taskId, task.text, task.notificationTime)
        }
    }
}