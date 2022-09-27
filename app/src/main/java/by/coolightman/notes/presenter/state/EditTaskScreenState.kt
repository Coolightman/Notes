package by.coolightman.notes.presenter.state

data class EditTaskScreenState(
    val text: String = "",
    val date: String = "",
    val color: Long = 0L,
    val isImportant: Boolean = false
)
