package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteSelectedTasksUseCase @Inject constructor(
    private val repository: TaskRepository,
    private val deleteTaskUseCase: DeleteTaskUseCase
) {
    suspend operator fun invoke() {
        repository.getAll().first().filter { it.isSelected }.forEach {
            deleteTaskUseCase(it.id)
        }
    }
}
