package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long): Task = repository.getTask(taskId)
}
