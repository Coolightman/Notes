package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.NotificationRepository
import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(taskId: Long) {
        notificationRepository.deleteAllByTask(taskId)
        repository.delete(taskId)
    }
}
