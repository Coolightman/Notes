package by.coolightman.notes.ui.screens.notesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.domain.usecase.folders.GetAllMainFoldersUseCase
import by.coolightman.notes.domain.usecase.folders.GetFoldersTrashCountUseCase
import by.coolightman.notes.domain.usecase.folders.PutFoldersInTrashUseCase
import by.coolightman.notes.domain.usecase.notes.*
import by.coolightman.notes.domain.usecase.preferences.*
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesTrashCountUseCase: GetNotesTrashCountUseCase,
    private val getFoldersTrashCountUseCase: GetFoldersTrashCountUseCase,
    private val getAllMainNotesUseCase: GetAllMainNotesUseCase,
    private val putNotesInTrashUseCase: PutNotesInTrashUseCase,
    private val putIntPreferenceUseCase: PutIntPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val putStringPreferenceUseCase: PutStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase,
    private val switchNoteCollapseUseCase: SwitchNoteCollapseUseCase,
    private val putBooleanPreferenceUseCase: PutBooleanPreferenceUseCase,
    private val getAllMainFoldersUseCase: GetAllMainFoldersUseCase,
    private val putFoldersInTrashUseCase: PutFoldersInTrashUseCase
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
        getFolders()
        getTrashCount()
        getIsShowDatePref()
        getIsColoredBackground()
        getIsShowUpdateDialog()
    }

    private fun getFolders() {
        viewModelScope.launch {
            getAllMainFoldersUseCase().collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(folders = it)
                }
            }
        }
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
                getAllMainNotesUseCase(sortFilter.first, sortFilter.second)
            }.collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(
                        notes = it,
                        sortByIndex = sortBy.first().ordinal,
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
            val notesTrashCountFlow = getNotesTrashCountUseCase()
            val foldersTrashCountFlow = getFoldersTrashCountUseCase()

            combine(notesTrashCountFlow, foldersTrashCountFlow) { el1, el2 ->
                el1 + el2
            }.collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(trashCount = it)
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

    fun putSelectedInTrash() {
        viewModelScope.launch {
            val selectedNotes = uiState.value.notes.filter { it.isSelected }
            val selectedFolders = uiState.value.folders.filter { it.isSelected }
            putNotesInTrashUseCase(selectedNotes)
            putFoldersInTrashUseCase(selectedFolders)
        }
    }

    fun switchIsSelected(noteId: Long) {
        val updatedNotes = uiState.value.notes
            .map {
                if (it.id == noteId) it.copy(isSelected = !it.isSelected)
                else it
            }

        _uiState.update { currentState ->
            currentState.copy(notes = updatedNotes)
        }
    }

    fun switchFolderIsSelected(folderId: Long) {
        val updatedFolders = uiState.value.folders
            .map {
                if (it.id == folderId) it.copy(isSelected = !it.isSelected)
                else it
            }

        _uiState.update { currentState ->
            currentState.copy(folders = updatedFolders)
        }
    }

    fun resetSelections() {
        val updatedNotes = uiState.value.notes
            .map { it.copy(isSelected = false) }

        val updatedFolders = uiState.value.folders
            .map { it.copy(isSelected = false) }

        _uiState.update { currentState ->
            currentState.copy(
                notes = updatedNotes,
                folders = updatedFolders
            )
        }
    }

    fun setCurrentIsSelected(noteId: Long) {
        val updatedNotes = uiState.value.notes
            .map {
                if (it.id == noteId) it.copy(isSelected = true)
                else it
            }

        _uiState.update { currentState ->
            currentState.copy(notes = updatedNotes)
        }
    }

    fun setCurrentFolderIsSelected(folderId: Long) {
        val updatedFolders = uiState.value.folders
            .map {
                if (it.id == folderId) it.copy(isSelected = true)
                else it
            }

        _uiState.update { currentState ->
            currentState.copy(folders = updatedFolders)
        }
    }

    fun selectAllItems() {
        val updatedNotes = uiState.value.notes
            .map { it.copy(isSelected = true) }

        val updatedFolders = uiState.value.folders
            .map { it.copy(isSelected = true) }

        _uiState.update { currentState ->
            currentState.copy(
                notes = updatedNotes,
                folders = updatedFolders
            )
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
