package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long){
        hideTask(taskId)
        delay(5000L)
        repository.delete(taskId)
    }

    private suspend fun hideTask(taskId: Long) {
        val task = repository.getTask(taskId)
        val hiddenTask = task.copy(isHidden = true)
        repository.update(hiddenTask)
    }
}