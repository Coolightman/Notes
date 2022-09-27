package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class SwitchTaskActivityUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long){
        val task = repository.getTask(taskId)
        val switched = task.copy(
            isActive = !task.isActive
        )
        repository.update(switched)
    }
}