package by.coolightman.notes.presenter.state

import by.coolightman.notes.domain.model.Note

data class NotesScreenState(
    val list: List<Note> = emptyList()
)
