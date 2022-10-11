package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke() = repository.getAll().map { taskList ->
        taskList
            .sortedBy { it.createdAt }
            .sortedByDescending { it.isImportant }
            .sortedByDescending { it.isActive }
    }
}