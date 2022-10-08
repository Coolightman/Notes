package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class SetIsSelectedTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long) {
        val task = repository.getTask(taskId)
        val editedNote = task.copy(
            isSelected = !task.isSelected
        )
        repository.update(editedNote)
    }
}