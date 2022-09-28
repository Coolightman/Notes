package by.coolightman.notes.ui.state

import by.coolightman.notes.domain.model.Note

data class NotesTrashScreenState(
    val list: List<Note> = emptyList()
)
