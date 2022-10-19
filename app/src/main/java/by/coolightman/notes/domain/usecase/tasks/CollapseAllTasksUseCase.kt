package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CollapseAllTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() {
        val list = repository.getAll().first().filter { it.isCollapsable }
        val editedList = list.map { it.copy(isCollapsed = true) }
        repository.updateList(editedList)
    }
}
