package by.coolightman.notes.ui.screens.notesScreen

import by.coolightman.notes.domain.model.Note

data class NotesUiState(
    val list: List<Note> = emptyList(),
    val trashCount: Int = 0
)
