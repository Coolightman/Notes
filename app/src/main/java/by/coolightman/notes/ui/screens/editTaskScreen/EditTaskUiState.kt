package by.coolightman.notes.ui.screens.editTaskScreen

data class EditTaskUiState(
    val text: String = "",
    val date: String = "",
    val colorIndex: Int = 0,
    val isImportant: Boolean = false
)