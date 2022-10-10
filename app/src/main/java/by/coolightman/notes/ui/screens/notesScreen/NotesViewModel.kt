package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortNotesBy
import by.coolightman.notes.domain.usecase.notes.*
import by.coolightman.notes.domain.usecase.preferences.*
import by.coolightman.notes.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesTrashCountUseCase: GetNotesTrashCountUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val putSelectedNotesInTrashUseCase: PutSelectedNotesInTrashUseCase,
    private val putIntPreferenceUseCase: PutIntPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val putStringPreferenceUseCase: PutStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
    private val setIsSelectedNoteUseCase: SetIsSelectedNoteUseCase,
    private val resetNotesSelectionsUseCase: ResetNotesSelectionsUseCase,
    private val selectAllNotesUseCase: SelectAllNotesUseCase,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase
) : ViewModel() {

    var uiState by mutableStateOf(NotesUiState())
        private set

    private val sortNotesBy: Flow<SortNotesBy> =
        getIntPreferenceUseCase(SORT_NOTES_BY_KEY)
            .map { value -> SortNotesBy.values()[value] }

    private val filterSelection: Flow<List<Boolean>> =
        getStringPreferenceUseCase(NOTES_FILTER_SELECTION)
            .map { convertPrefStringToFilterSelectionList(it) }

    private val sortFilter: Flow<Pair<SortNotesBy, List<Boolean>>> =
        sortNotesBy.combine(filterSelection){sort, filter -> Pair(sort, filter) }

    init {
        getNotes()
        getTrashCount()
        getIsShowDatePref()
    }

    private fun getIsShowDatePref() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_SHOW_NOTE_DATE).collectLatest {
                uiState = uiState.copy(
                    isShowNoteDate = it
                )
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getNotes() {
        viewModelScope.launch {
            sortFilter.flatMapLatest { sortFilter ->
                getAllNotesUseCase(sortFilter.first, sortFilter.second)
            }.collectLatest {
                uiState = uiState.copy(
                    list = it,
                    sortByIndex = sortNotesBy.first().ordinal,
                    notesCount = it.size,
                    selectedCount = it.filter { note -> note.isSelected }.size,
                    currentFilterSelection = filterSelection.first()
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

    fun selectAllNotes() {
        viewModelScope.launch {
            selectAllNotesUseCase()
        }
    }

    fun setFilterSelection(selection: List<Boolean>) {
        viewModelScope.launch {
            putStringPreferenceUseCase(NOTES_FILTER_SELECTION, selection.toPreferenceString())
        }
    }
}