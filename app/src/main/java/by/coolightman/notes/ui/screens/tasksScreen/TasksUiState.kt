package by.coolightman.notes.ui.screens.tasksScreen

import by.coolightman.notes.domain.model.Task

data class TasksUiState(
    val list: List<Task> = emptyList()
)