package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.TaskRepository
import by.coolightman.notes.util.isOld
import java.util.*
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long): Task {
        val task = repository.getTask(taskId)
        return if (task.notificationTime.isOld()) {
            task.copy(notificationTime = Calendar.getInstance(Locale.getDefault()))
        } else {
            task
        }
    }
}