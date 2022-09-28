package by.coolightman.notes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortNoteBy
import by.coolightman.notes.domain.usecase.notes.GetAllNotesSortByUseCase
import by.coolightman.notes.domain.usecase.notes.GetNotesTrashCountUseCase
import by.coolightman.notes.domain.usecase.notes.PutNoteInTrashUseCase
import by.coolightman.notes.ui.state.NotesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesTrashCountUseCase: GetNotesTrashCountUseCase,
    private val getAllNotesSortByUseCase: GetAllNotesSortByUseCase,
    private val putNoteInTrashUseCase: PutNoteInTrashUseCase
) : ViewModel() {

    var state by mutableStateOf(NotesScreenState())
        private set

    init {
        getNotes()
        getTrashCount()
    }

    private fun getNotes() {
        viewModelScope.launch {
            getAllNotesSortByUseCase(SortNoteBy.CREATE_DATE_DESC).collect {
                state = state.copy(
                    list = it
                )
            }
        }
    }

    private fun getTrashCount() {
        viewModelScope.launch {
            getNotesTrashCountUseCase().collect {
                state = state.copy(trashCount = it)
            }
        }
    }

    fun putInNoteTrash(noteId: Long) {
        viewModelScope.launch {
            putNoteInTrashUseCase(noteId)
        }
    }
}