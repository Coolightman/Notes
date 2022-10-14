package by.coolightman.notes.ui.screens.notesScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.domain.usecase.notes.*
import by.coolightman.notes.domain.usecase.preferences.*
import by.coolightman.notes.ui.model.NotesViewMode
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
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase,
    private val switchNoteExpandUseCase: SwitchNoteExpandUseCase
) : ViewModel() {

    var uiState by mutableStateOf(NotesUiState())
        private set

    private val sortBy: Flow<SortBy> =
        getIntPreferenceUseCase(SORT_NOTES_BY_KEY)
            .map { value -> SortBy.values()[value] }

    private val filterSelection: Flow<List<Boolean>> =
        getStringPreferenceUseCase(NOTES_FILTER_SELECTION)
            .map { convertPrefStringToFilterSelectionList(it) }

    private val sortFilter: Flow<Pair<SortBy, List<Boolean>>> =
        sortBy.combine(filterSelection){ sort, filter -> Pair(sort, filter) }

    init {
        getNotes()
        getTrashCount()
        getIsShowDatePref()
        getNotesViewMode()
        getIsColorfulBackground()
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
                    sortByIndex = sortBy.first().ordinal,
                    notesCount = it.size,
                    selectedCount = it.filter { note -> note.isSelected }.size,
                    currentFilterSelection = filterSelection.first()
                )
            }
        }
    }

    fun setSortBy(sortBy: SortBy) {
        viewModelScope.launch { putIntPreferenceUseCase(SORT_NOTES_BY_KEY, sortBy.ordinal) }
    }

    private fun getTrashCount() {
        viewModelScope.launch {
            getNotesTrashCountUseCase().collect {
                uiState = uiState.copy(trashCount = it)
            }
        }
    }

    private fun getNotesViewMode() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NOTES_VIEW_MODE).collectLatest {
                uiState = uiState.copy(
                    currentNotesViewMode = NotesViewMode.values()[it]
                )
            }
        }
    }

    private fun getIsColorfulBackground() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_NOTES_COLORED_BACK).collectLatest {
                uiState = uiState.copy(
                    isColoredBackground = it
                )
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

    fun switchExpand(noteId: Long){
        viewModelScope.launch {
            switchNoteExpandUseCase(noteId)
        }
    }
}