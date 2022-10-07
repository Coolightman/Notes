package by.coolightman.notes.ui.screens.editTaskScreen

data class EditTaskUiState(
    val text: String = "",
    val createdAt: String = "",
    val editedAt: String = "",
    val colorIndex: Int = 0,
    val isImportant: Boolean = false,
    val newTaskColorPrefIndex: Int = 0
)