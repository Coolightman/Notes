package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(sortBy: SortBy, filterBy: List<Boolean>): Flow<List<Task>> {
        val tasksFlow = repository.getAll()
        val filteredFlow = filterList(tasksFlow, filterBy)
        return sortList(filteredFlow, sortBy)
    }

    private fun filterList(tasksFlow: Flow<List<Task>>, filterBy: List<Boolean>): Flow<List<Task>> {
        return tasksFlow.map { tasksList ->
            val filteredList = mutableListOf<Task>()
            filteredList.apply {
                if (filterBy.contains(true)) {
                    filterBy.forEachIndexed { index, filterState ->
                        if (filterState) {
                            addAll(tasksList.filter { task -> task.colorIndex == index })
                        }
                    }
                } else {
                    addAll(tasksList)
                }
            }
        }
    }

    private fun sortList(tasksFlow: Flow<List<Task>>, sortBy: SortBy): Flow<List<Task>> {
        return tasksFlow.map { tasksList ->
            when (sortBy) {
                SortBy.COLOR -> {
                    tasksList.sortedBy { it.colorIndex }
                }
                SortBy.COLOR_DESC -> {
                    tasksList.sortedByDescending { it.colorIndex }
                }
                SortBy.EDIT_DATE -> {
                    tasksList.sortedBy {
                        if (it.isEdited) {
                            it.editedAt
                        } else {
                            it.createdAt
                        }
                    }
                }
                SortBy.EDIT_DATE_DESC -> {
                    tasksList.sortedByDescending {
                        if (it.isEdited) {
                            it.editedAt
                        } else {
                            it.createdAt
                        }
                    }
                }
                SortBy.CREATE_DATE -> {
                    tasksList.sortedBy { it.createdAt }
                }
                SortBy.CREATE_DATE_DESC -> {
                    tasksList.sortedByDescending { it.createdAt }
                }
            }.sortedByDescending { it.isImportant }.sortedByDescending { it.isActive }
        }
    }
}