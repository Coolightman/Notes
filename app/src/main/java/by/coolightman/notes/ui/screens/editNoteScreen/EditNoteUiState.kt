package by.coolightman.notes.ui.screens.editNoteScreen

data class EditNoteUiState(
    val title: String = "",
    val text: String = "",
    val createdAt: String = "",
    val colorIndex: Int = 0,
    val newNoteColorPrefIndex: Int = 0
)
