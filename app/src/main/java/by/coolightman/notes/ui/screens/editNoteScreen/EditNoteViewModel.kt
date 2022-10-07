package by.coolightman.notes.ui.screens.editNoteScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.usecase.notes.CreateNoteUseCase
import by.coolightman.notes.domain.usecase.notes.GetNoteUseCase
import by.coolightman.notes.domain.usecase.notes.UpdateNoteUseCase
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.util.ARG_NOTE_ID
import by.coolightman.notes.util.NEW_NOTE_COLOR_KEY
import by.coolightman.notes.util.toFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNoteUseCase: GetNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase
) : ViewModel() {

    var uiState by mutableStateOf(EditNoteUiState())
        private set

    private var note: Note? = null

    init {
        val noteId = savedStateHandle.get<Long>(ARG_NOTE_ID) ?: 0L
        if (noteId != 0L) {
            getNote(noteId)
        } else{
            getNewNoteColorPreference()
        }
    }

    private fun getNewNoteColorPreference() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_NOTE_COLOR_KEY).collectLatest {
                uiState = uiState.copy(
                    newNoteColorPrefIndex = it
                )
            }
        }
    }

    private fun getNote(noteId: Long) {
        viewModelScope.launch {
            note = getNoteUseCase(noteId)
            note?.let {
                uiState = uiState.copy(
                    title = it.title,
                    text = it.text,
                    createdAt = it.createdAt.toFormattedDate(),
                    colorIndex = it.colorIndex
                )
            }
        }
    }

    fun saveNote(
        title: String, text: String, colorIndex: Int
    ) {
        viewModelScope.launch {
            note?.let {
                val updatedNote = it.copy(
                    title = title,
                    text = text,
                    colorIndex = colorIndex,
                    isEdited = true,
                    editedAt = System.currentTimeMillis()
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
                isShowDate = false
            )
            createNoteUseCase(createdNote)
        }
    }
}