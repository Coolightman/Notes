package by.coolightman.notes.ui.screens.editNoteScreen

data class EditNoteUiState(
    val title: String = "",
    val text: String = "",
    val date: String = "",
    val color: Long = 0L,
    val trashCount: Int = 0
)
