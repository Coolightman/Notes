package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class SwitchTaskCollapseUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long){
        val task = repository.getTask(taskId)
        val editedTask = task.copy(
            isCollapsed = !task.isCollapsed
        )
        repository.update(editedTask)
    }
}