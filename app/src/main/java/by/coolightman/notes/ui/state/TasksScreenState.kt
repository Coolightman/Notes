package by.coolightman.notes.ui.state

import by.coolightman.notes.domain.model.Task

data class TasksScreenState(
    val list: List<Task> = emptyList()
)
