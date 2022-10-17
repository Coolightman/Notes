package by.coolightman.notes.ui.screens.tasksScreen

import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.ui.model.ItemColor

data class TasksUiState(
    val list: List<Task> = emptyList(),
    val activeTasksCount: Int = 0,
    val inactiveTasksCount: Int = 0,
    val selectedCount: Int = 0,
    val isListHasCollapsable: Boolean = false,
    val sortByIndex: Int = 2,
    val currentFilterSelection: List<Boolean> = ItemColor.values().map { false }
)
