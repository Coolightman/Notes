package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortNotesBy
import by.coolightman.notes.domain.usecase.notes.*
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutIntPreferenceUseCase
import by.coolightman.notes.util.SORT_NOTES_BY_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesTrashCountUseCase: GetNotesTrashCountUseCase,
    private val getAllNotesSortByUseCase: GetAllNotesSortByUseCase,
    private val putSelectedNotesInTrashUseCase: PutSelectedNotesInTrashUseCase,
    private val putIntPreferenceUseCase: PutIntPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val setIsSelectedNoteUseCase: SetIsSelectedNoteUseCase,
    private val resetNotesSelectionsUseCase: ResetNotesSelectionsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(NotesUiState())
        private set

    private val sortNotesBy: Flow<SortNotesBy> =
        getIntPreferenceUseCase(SORT_NOTES_BY_KEY)
            .map { value -> SortNotesBy.values()[value] }

    init {
        getNotes()
        getTrashCount()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getNotes() {
        viewModelScope.launch {
            sortNotesBy.flatMapLatest { sortBy ->
                getAllNotesSortByUseCase(sortBy)
            }.collectLatest { it ->
                uiState = uiState.copy(
                    list = it,
                    sortByIndex = sortNotesBy.first().ordinal,
                    notesCount = it.size,
                    selectedCount = it.filter { note -> note.isSelected }.size
                )
            }
        }
    }

    fun setSortBy(sortBy: SortNotesBy) {
        viewModelScope.launch { putIntPreferenceUseCase(SORT_NOTES_BY_KEY, sortBy.ordinal) }
    }

    private fun getTrashCount() {
        viewModelScope.launch {
            getNotesTrashCountUseCase().collect {
                uiState = uiState.copy(trashCount = it)
            }
        }
    }

    fun putSelectedNotesInTrash() {
        viewModelScope.launch {
            putSelectedNotesInTrashUseCase()
        }
    }

    fun setIsSelectedNote(noteId: Long) {
        viewModelScope.launch {
            setIsSelectedNoteUseCase(noteId)
        }
    }

    fun resetSelections(noteId: Long) {
        viewModelScope.launch {
            resetNotesSelectionsUseCase()
            setIsSelectedNoteUseCase(noteId)
        }
    }
}