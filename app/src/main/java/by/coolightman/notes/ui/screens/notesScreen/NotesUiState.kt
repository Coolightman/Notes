package by.coolightman.notes.ui.screens.notesScreen

import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.ui.model.NotesViewMode

data class NotesUiState(
    val list: List<Note> = emptyList(),
    val trashCount: Int = 0,
    val sortByIndex: Int = 0,
    val notesCount: Int = 0,
    val selectedCount: Int = 0,
    val isShowNoteDate: Boolean = false,
    val currentFilterSelection: List<Boolean> = ItemColor.values().map { false },
    val currentNotesViewMode: NotesViewMode = NotesViewMode.LIST
)
