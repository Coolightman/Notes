package by.coolightman.notes.ui.screens.searchNoteScreen

import by.coolightman.notes.domain.model.Note

data class SearchNoteUiState(
    val list: List<Note> = emptyList(),
    val searchKey: String = ""
)