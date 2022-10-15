package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val removeNotificationUseCase: RemoveNotificationUseCase
) {
    suspend operator fun invoke(taskId: Long) {
        repository.delete(taskId)
        removeNotificationUseCase(taskId)
    }
}