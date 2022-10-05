package by.coolightman.notes.ui.screens.notesTrashScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.notes.*
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

    var uiState by mutableStateOf(NotesTrashUiState())
        private set

    init {
        getTrash()
    }

    private fun getTrash() {
        viewModelScope.launch {
            getNotesTrashUseCase().collect {
                uiState = uiState.copy(
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