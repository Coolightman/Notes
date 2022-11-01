package by.coolightman.notes.ui.screens.notesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.domain.usecase.notes.*
import by.coolightman.notes.domain.usecase.preferences.*
import by.coolightman.notes.ui.model.ItemColor
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
    private val switchIsSelectedNoteUseCase: SwitchIsSelectedNoteUseCase,
    private val resetNotesSelectionsUseCase: ResetNotesSelectionsUseCase,
    private val selectAllNotesUseCase: SelectAllNotesUseCase,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase,
    private val switchNoteCollapseUseCase: SwitchNoteCollapseUseCase,
    private val putBooleanPreferenceUseCase: PutBooleanPreferenceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val sortBy: Flow<SortBy> = getIntPreferenceUseCase(
        SORT_NOTES_BY_KEY,
        SortBy.CREATE_DATE.ordinal
    ).map { value -> SortBy.values()[value] }

    private val filterSelection: Flow<List<Boolean>> = getStringPreferenceUseCase(
        NOTES_FILTER_SELECTION, ItemColor.values().map { false }.toPreferenceString()
    ).map { convertPrefStringToFilterSelectionList(it) }

    private val sortFilter: Flow<Pair<SortBy, List<Boolean>>> =
        sortBy.combine(filterSelection) { sort, filter -> Pair(sort, filter) }

    init {
        getNotes()
        getTrashCount()
        getIsShowDatePref()
        getNotesViewMode()
        getIsColoredBackground()
        getIsShowUpdateDialog()
    }

    private fun getIsShowDatePref() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_SHOW_NOTE_DATE, true).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isShowNoteDate = it)
                }
            }
        }
    }

    private fun getIsShowUpdateDialog() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(SHOW_UPDATE_DIALOG_EXTRA, false).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isShowUpdateAppDialog = it)
                }
            }
        }
    }

    fun notShowMoreUpdateDialog() {
        viewModelScope.launch { putBooleanPreferenceUseCase(SHOW_UPDATE_DIALOG_EXTRA, false) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getNotes() {
        viewModelScope.launch {
            sortFilter.flatMapLatest { sortFilter ->
                getAllNotesUseCase(sortFilter.first, sortFilter.second)
            }.collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(
                        list = it,
                        sortByIndex = sortBy.first().ordinal,
                        notesCount = it.size,
                        selectedCount = it.filter { note -> note.isSelected }.size,
                        currentFilterSelection = filterSelection.first()
                    )
                }
            }
        }
    }

    fun setSortBy(sortBy: SortBy) {
        viewModelScope.launch { putIntPreferenceUseCase(SORT_NOTES_BY_KEY, sortBy.ordinal) }
    }

    private fun getTrashCount() {
        viewModelScope.launch {
            getNotesTrashCountUseCase().collect {
                _uiState.update { currentState ->
                    currentState.copy(trashCount = it)
                }
            }
        }
    }

    private fun getNotesViewMode() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NOTES_VIEW_MODE, NotesViewMode.LIST.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(currentNotesViewMode = NotesViewMode.values()[it])
                }
            }
        }
    }

    private fun getIsColoredBackground() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_NOTES_COLORED_BACK, true).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isColoredBackground = it)
                }
            }
        }
    }

    fun putSelectedNotesInTrash() {
        viewModelScope.launch {
            putSelectedNotesInTrashUseCase()
        }
    }

    fun switchIsSelectedNote(noteId: Long) {
        viewModelScope.launch {
            switchIsSelectedNoteUseCase(noteId)
        }
    }

    fun resetSelections(noteId: Long) {
        viewModelScope.launch {
            resetNotesSelectionsUseCase()
            switchIsSelectedNoteUseCase(noteId)
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

    fun switchCollapse(noteId: Long) {
        viewModelScope.launch {
            switchNoteCollapseUseCase(noteId)
        }
    }
}
