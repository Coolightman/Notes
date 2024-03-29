package by.coolightman.notes.ui.screens.notesScreen

import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.ui.model.ItemColor

data class NotesUiState(
    val notes: List<Note> = emptyList(),
    val folders: List<Folder> = emptyList(),
    val foldersToMove: List<Folder> = emptyList(),
    val trashCount: Int = 0,
    val sortByIndex: Int = 0,
    val isShowNoteDate: Boolean = false,
    val isColoredBackground: Boolean = false,
    val isShowUpdateAppDialog: Boolean = false,
    val currentFilterSelection: List<Boolean> = ItemColor.values().map { false },
)
