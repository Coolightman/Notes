package by.coolightman.notes.presenter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import by.coolightman.notes.presenter.state.EditNoteScreenState
import by.coolightman.notes.util.ARG_NOTE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(EditNoteScreenState())
        private set

    init {
        val noteId = savedStateHandle.get<Long>(ARG_NOTE_ID) ?: 0L
        state = state.copy(
            text = noteId.toString()
        )
    }
}