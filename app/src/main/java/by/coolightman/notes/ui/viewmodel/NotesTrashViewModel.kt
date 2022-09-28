package by.coolightman.notes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.notes.*
import by.coolightman.notes.ui.state.NotesTrashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesTrashViewModel @Inject constructor(
    private val getNotesTrashUseCase: GetNotesTrashUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val restoreNoteUseCase: RestoreNoteUseCase,
    private val deleteAllNotesTrashUseCase: DeleteAllNotesTrashUseCase,
    private val restoreAllNotesTrashUseCase: RestoreAllNotesTrashUseCase
) : ViewModel() {

    var state by mutableStateOf(NotesTrashScreenState())
        private set

    init {
        getTrash()
    }

    private fun getTrash() {
        viewModelScope.launch {
            getNotesTrashUseCase().collect {
                state = state.copy(
                    list = it
                )
            }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
        }
    }

    fun restoreNote(noteId: Long) {
        viewModelScope.launch {
            restoreNoteUseCase(noteId)
        }
    }

    fun deleteAllTrash() {
        viewModelScope.launch {
            deleteAllNotesTrashUseCase()
        }
    }

    fun restoreAllTrash() {
        viewModelScope.launch {
            restoreAllNotesTrashUseCase()
        }
    }
}