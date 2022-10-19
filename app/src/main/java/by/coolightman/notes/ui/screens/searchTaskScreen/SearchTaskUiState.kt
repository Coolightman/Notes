package by.coolightman.notes.ui.screens.searchTaskScreen

import by.coolightman.notes.domain.model.Task

data class SearchTaskUiState(
    val list: List<Task> = emptyList(),
)
