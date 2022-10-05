package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortNotesBy
import by.coolightman.notes.domain.repository.PreferencesRepository
import by.coolightman.notes.domain.usecase.notes.GetAllNotesSortByUseCase
import by.coolightman.notes.domain.usecase.notes.GetNotesTrashCountUseCase
import by.coolightman.notes.domain.usecase.notes.PutNoteInTrashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesTrashCountUseCase: GetNotesTrashCountUseCase,
    private val getAllNotesSortByUseCase: GetAllNotesSortByUseCase,
    private val putNoteInTrashUseCase: PutNoteInTrashUseCase,
    private val prefRepo: PreferencesRepository
) : ViewModel() {

    var uiState by mutableStateOf(NotesUiState())
        private set

    private val sortNotesBy: Flow<SortNotesBy> =
        prefRepo.getInt(SORT_NOTES_BY_KEY)
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
            }.collectLatest {
                uiState = uiState.copy(
                    list = it,
                    sortByIndex = sortNotesBy.first().ordinal
                )
            }
        }
    }

    fun setSortBy(sortBy: SortNotesBy) {
        viewModelScope.launch { prefRepo.putInt(SORT_NOTES_BY_KEY, sortBy.ordinal) }
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

    companion object {
        private const val SORT_NOTES_BY_KEY = "SortNotesByKey"
    }
}