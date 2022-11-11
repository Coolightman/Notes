package by.coolightman.notes.ui.screens.editNoteScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.usecase.notes.CreateNoteUseCase
import by.coolightman.notes.domain.usecase.notes.GetNoteUseCase
import by.coolightman.notes.domain.usecase.notes.PutNoteInTrashUseCase
import by.coolightman.notes.domain.usecase.notes.UpdateNoteUseCase
import by.coolightman.notes.domain.usecase.preferences.GetBooleanPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.util.ARG_FOLDER_ID
import by.coolightman.notes.util.ARG_NOTE_ID
import by.coolightman.notes.util.IS_NOTES_COLORED_BACK
import by.coolightman.notes.util.NEW_NOTE_COLOR_KEY
import by.coolightman.notes.util.toFormattedFullDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val putNoteInTrashUseCase: PutNoteInTrashUseCase,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditNoteUiState())
    val uiState: StateFlow<EditNoteUiState> = _uiState.asStateFlow()

    private var note: Note? = null
    private var folderId: Long = 0L

    init {
        val noteId = savedStateHandle.get<Long>(ARG_NOTE_ID) ?: 0L
        folderId = savedStateHandle.get<Long>(ARG_FOLDER_ID) ?: 0L
        if (noteId != 0L) {
            getNote(noteId)
        } else {
            getNewNoteColorPreference()
        }
        getColoredBackgroundPreference()
    }

    private fun getColoredBackgroundPreference() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_NOTES_COLORED_BACK, true).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isColoredBackground = it)
                }
            }
        }
    }

    private fun getNewNoteColorPreference() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_NOTE_COLOR_KEY, ItemColor.GRAY.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(colorIndex = it)
                }
            }
        }
    }

    private fun getNote(noteId: Long) {
        viewModelScope.launch {
            note = getNoteUseCase(noteId)
            note?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        title = it.title,
                        text = it.text,
                        createdAt = it.createdAt.toFormattedFullDate(),
                        editedAt = it.editedAt.toFormattedFullDate(),
                        colorIndex = it.colorIndex,
                        isAllowToCollapse = it.isCollapsable,
                        isPinned = it.isPinned
                    )
                }
            }
        }
    }

    fun saveNote(
        title: String,
        text: String,
        colorIndex: Int,
        isCollapsable: Boolean,
        isPinned: Boolean
    ) {
        viewModelScope.launch {
            note?.let {
                val updatedNote = it.copy(
                    title = title,
                    text = text,
                    colorIndex = colorIndex,
                    isEdited = true,
                    editedAt = System.currentTimeMillis(),
                    isCollapsable = isCollapsable,
                    isCollapsed =
                    if (!isCollapsable) false
                    else it.isCollapsed,
                    isPinned = isPinned,
                    folderId = it.folderId
                )
                updateNoteUseCase(updatedNote)
                return@launch
            }

            val createdNote = Note(
                title = title,
                text = text,
                colorIndex = colorIndex,
                createdAt = System.currentTimeMillis(),
                editedAt = 0L,
                isEdited = false,
                isInTrash = false,
                isShowDate = false,
                isSelected = false,
                isCollapsable = isCollapsable,
                isCollapsed = false,
                isPinned = isPinned,
                folderId = folderId
            )
            createNoteUseCase(createdNote)
        }
    }

    fun sentNoteToTrash() {
        viewModelScope.launch {
            note?.let {
                putNoteInTrashUseCase(it.id)
            }
        }
    }
}
