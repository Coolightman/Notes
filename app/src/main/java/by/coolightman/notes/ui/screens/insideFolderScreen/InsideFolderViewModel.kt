package by.coolightman.notes.ui.screens.insideFolderScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.domain.usecase.folders.GetAllActiveFoldersUseCase
import by.coolightman.notes.domain.usecase.folders.GetAllFoldersByExternalFolderUseCase
import by.coolightman.notes.domain.usecase.folders.GetFolderUseCase
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
class InsideFolderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getNotesTrashCountUseCase: GetNotesTrashCountUseCase,
    private val getFoldersTrashCountUseCase: GetFoldersTrashCountUseCase,
    private val getAllNotesByFolderUseCase: GetAllNotesByFolderUseCase,
    private val putNotesInTrashUseCase: PutNotesInTrashUseCase,
    private val putIntPreferenceUseCase: PutIntPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val putStringPreferenceUseCase: PutStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase,
    private val switchNoteCollapseUseCase: SwitchNoteCollapseUseCase,
    private val putFoldersInTrashUseCase: PutFoldersInTrashUseCase,
    private val getAllFoldersByExternalFolderUseCase: GetAllFoldersByExternalFolderUseCase,
    private val getFolderUseCase: GetFolderUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getAllActiveFoldersUseCase: GetAllActiveFoldersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsideFolderUiState())
    val uiState: StateFlow<InsideFolderUiState> = _uiState.asStateFlow()

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
        val folderId = savedStateHandle.get<Long>(ARG_INTO_FOLDER_ID) ?: 0L
        if (folderId != 0L) {
            getCurrentFolder(folderId)
            getNotes(folderId)
            getFolders(folderId)
            getTrashCount()
            getIsShowDatePref()
            getIsColoredBackground()
        }
    }

    private fun getCurrentFolder(folderId: Long) {
        viewModelScope.launch {
            getFolderUseCase(folderId).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(
                        currentFolderId = it.id,
                        currentFolderTitle = it.title
                    )
                }
            }
        }
    }

    private fun getFolders(folderId: Long) {
        viewModelScope.launch {
            getAllFoldersByExternalFolderUseCase(folderId).collectLatest {
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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getNotes(folderId: Long) {
        viewModelScope.launch {
            sortFilter.flatMapLatest { sortFilter ->
                getAllNotesByFolderUseCase(sortFilter.first, sortFilter.second, folderId)
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

    fun getAllFoldersToMove() {
        viewModelScope.launch {
            getAllActiveFoldersUseCase().collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(foldersToMove = it)
                }
            }
        }
    }

    fun moveSelectedToFolder(folderId: Long) {
        viewModelScope.launch {
            uiState.value.notes
                .filter { it.isSelected }
                .map { it.copy(folderId = folderId) }
                .forEach { updateNoteUseCase(it) }
        }
    }
}
