package by.coolightman.notes.ui.screens.insideFolderScreen

import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.ui.model.ItemColor

data class InsideFolderUiState(
    val currentFolderTitle: String = "",
    val currentFolderId: Long = 0L,
    val notes: List<Note> = emptyList(),
    val folders: List<Folder> = emptyList(),
    val trashCount: Int = 0,
    val sortByIndex: Int = 0,
    val isShowNoteDate: Boolean = false,
    val isColoredBackground: Boolean = false,
    val currentFilterSelection: List<Boolean> = ItemColor.values().map { false }
)
