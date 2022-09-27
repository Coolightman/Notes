package by.coolightman.notes.presenter.state

data class EditNoteScreenState(
    val title: String = "",
    val text: String = "",
    val date: String = "",
    val color: Long = 0L
)
