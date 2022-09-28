package by.coolightman.notes.ui.state

data class EditTaskScreenState(
    val text: String = "",
    val date: String = "",
    val color: Long = 0L,
    val isImportant: Boolean = false
)
