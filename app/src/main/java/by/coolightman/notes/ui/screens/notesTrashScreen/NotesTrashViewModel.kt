package by.coolightman.notes.ui.screens.notesTrashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.usecase.folders.DeleteAllFoldersTrashUseCase
import by.coolightman.notes.domain.usecase.folders.DeleteFolderUseCase
import by.coolightman.notes.domain.usecase.folders.GetFoldersTrashUseCase
import by.coolightman.notes.domain.usecase.folders.RestoreAllFoldersTrashUseCase
import by.coolightman.notes.domain.usecase.folders.RestoreFolderUseCase
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
import kotlinx.coroutines.flow.collectLatest
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
    private val switchNoteCollapseUseCase: SwitchNoteCollapseUseCase,
    private val getFoldersTrashUseCase: GetFoldersTrashUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val deleteAllFoldersTrashUseCase: DeleteAllFoldersTrashUseCase,
    private val restoreAllFoldersTrashUseCase: RestoreAllFoldersTrashUseCase,
    private val restoreFolderUseCase: RestoreFolderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesTrashUiState())
    val uiState: StateFlow<NotesTrashUiState> = _uiState.asStateFlow()

    private var deletedNoteCash: Note? = null

    init {
        getTrash()
    }

    private fun getTrash() {
        viewModelScope.launch {
            launch {
                getNotesTrashUseCase().collectLatest {
                    _uiState.update { currentState ->
                        currentState.copy(notes = it)
                    }
                }
            }
            launch {
                getFoldersTrashUseCase().collectLatest {
                    _uiState.update { currentState ->
                        currentState.copy(folders = it)
                    }
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

    fun deleteFolder(folderId: Long) {
        viewModelScope.launch {
            deleteFolderUseCase(folderId)
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

    fun restoreFolder(folderId: Long) {
        viewModelScope.launch {
            restoreFolderUseCase(folderId)
        }
    }

    fun deleteAllTrash() {
        viewModelScope.launch {
            deleteAllNotesTrashUseCase()
            deleteAllFoldersTrashUseCase()
        }
    }

    fun restoreAllTrash() {
        viewModelScope.launch {
            restoreAllNotesTrashUseCase()
            restoreAllFoldersTrashUseCase()
        }
    }
}
