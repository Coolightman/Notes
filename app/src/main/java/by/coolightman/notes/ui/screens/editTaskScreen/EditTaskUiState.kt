package by.coolightman.notes.ui.screens.editTaskScreen

data class EditTaskUiState(
    val text: String = "",
    val date: String = "",
    val color: Long = 0L,
    val isImportant: Boolean = false
)