package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ResetTasksSelectionsUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() {
        val list = repository.getAll().first()
        val editedList = list.map {
            it.copy(
                isSelected = false
            )
        }
        repository.updateList(editedList)
    }
}