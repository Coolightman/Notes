package by.coolightman.notes.ui.screens.notesTrashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.usecase.notes.CreateNoteUseCase
import by.coolightman.notes.domain.usecase.notes.DeleteAllNotesTrashUseCase
import by.coolightman.notes.domain.usecase.notes.DeleteNoteUseCase
import by.coolightman.notes.domain.usecase.notes.GetNoteUseCase
import by.coolightman.notes.domain.usecase.notes.GetNotesTrashUseCase
import by.coolightman.notes.domain.usecase.notes.RestoreAllNotesTrashUseCase
import by.coolightman.notes.domain.usecase.notes.RestoreNoteUseCase
import by.coolightman.notes.domain.usecase.notes.SwitchNoteCollapseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesTrashViewModel @Inject constructor(
    private val getNotesTrashUseCase: GetNotesTrashUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val restoreNoteUseCase: RestoreNoteUseCase,
    private val deleteAllNotesTrashUseCase: DeleteAllNotesTrashUseCase,
    private val restoreAllNotesTrashUseCase: RestoreAllNotesTrashUseCase,
    private val getNoteUseCase: GetNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val switchNoteCollapseUseCase: SwitchNoteCollapseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesTrashUiState())
    val uiState: StateFlow<NotesTrashUiState> = _uiState.asStateFlow()

    private var deletedNoteCash: Note? = null

    init {
        getTrash()
    }

    private fun getTrash() {
        viewModelScope.launch {
            getNotesTrashUseCase().collect {
                _uiState.update { currentState ->
                    currentState.copy(list = it)
                }
            }
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            launch { deletedNoteCash = getNoteUseCase(noteId) }.join()
            deleteNoteUseCase(noteId)
        }
    }

    fun cancelDeletion() {
        viewModelScope.launch {
            deletedNoteCash?.let {
                createNoteUseCase(it)
            }
        }
    }

    fun switchCollapse(noteId: Long) {
        viewModelScope.launch {
            switchNoteCollapseUseCase(noteId)
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
