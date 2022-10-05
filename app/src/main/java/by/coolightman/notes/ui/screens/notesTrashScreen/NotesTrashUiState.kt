package by.coolightman.notes.ui.screens.notesTrashScreen

import by.coolightman.notes.domain.model.Note

data class NotesTrashUiState(
    val list: List<Note> = emptyList()
)
