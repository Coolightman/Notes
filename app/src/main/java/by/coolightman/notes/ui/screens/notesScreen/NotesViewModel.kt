package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortNoteBy
import by.coolightman.notes.domain.usecase.notes.GetAllNotesSortByUseCase
import by.coolightman.notes.domain.usecase.notes.GetNotesTrashCountUseCase
import by.coolightman.notes.domain.usecase.notes.PutNoteInTrashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesTrashCountUseCase: GetNotesTrashCountUseCase,
    private val getAllNotesSortByUseCase: GetAllNotesSortByUseCase,
    private val putNoteInTrashUseCase: PutNoteInTrashUseCase
) : ViewModel() {

    var uiState by mutableStateOf(NotesUiState())
        private set

    init {
        getNotes()
        getTrashCount()
    }

    private fun getNotes() {
        viewModelScope.launch {
            getAllNotesSortByUseCase(SortNoteBy.CREATE_DATE_DESC).collect {
                uiState = uiState.copy(list = it)
            }
        }
    }

    private fun getTrashCount() {
        viewModelScope.launch {
            getNotesTrashCountUseCase().collect {
                uiState = uiState.copy(trashCount = it)
            }
        }
    }

    fun putInNoteTrash(noteId: Long) {
        viewModelScope.launch {
            putNoteInTrashUseCase(noteId)
        }
    }
}