package by.coolightman.notes.ui.screens.notesTrashScreen

import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.domain.model.Note

data class NotesTrashUiState(
    val notes: List<Note> = emptyList(),
    val folders: List<Folder> = emptyList()
)
