package by.coolightman.notes.ui.screens.tasksScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.domain.usecase.preferences.GetBooleanPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetStringPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutBooleanPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutStringPreferenceUseCase
import by.coolightman.notes.domain.usecase.tasks.*
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val deleteInactiveTasksUseCase: DeleteInactiveTasksUseCase,
    private val switchTaskActivityUseCase: SwitchTaskActivityUseCase,
    private val deleteSelectedTasksUseCase: DeleteSelectedTasksUseCase,
    private val resetTasksSelectionsUseCase: ResetTasksSelectionsUseCase,
    private val switchIsSelectedTaskUseCase: SwitchIsSelectedTaskUseCase,
    private val selectAllTasksUseCase: SelectAllTasksUseCase,
    private val switchTaskCollapseUseCase: SwitchTaskCollapseUseCase,
    private val expandAllTasksUseCase: ExpandAllTasksUseCase,
    private val collapseAllTasksUseCase: CollapseAllTasksUseCase,
    private val putIntPreferenceUseCase: PutIntPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val putStringPreferenceUseCase: PutStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase,
    private val putBooleanPreferenceUseCase: PutBooleanPreferenceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    private val sortBy: Flow<SortBy> =
        getIntPreferenceUseCase(SORT_TASKS_BY_KEY, SortBy.CREATE_DATE.ordinal)
            .map { value -> SortBy.values()[value] }

    private val filterSelection: Flow<List<Boolean>> =
        getStringPreferenceUseCase(
            TASKS_FILTER_SELECTION,
            ItemColor.values().map { false }.toPreferenceString()
        ).map { convertPrefStringToFilterSelectionList(it) }

    private val sortFilter: Flow<Pair<SortBy, List<Boolean>>> =
        sortBy.combine(filterSelection) { sort, filter -> Pair(sort, filter) }

    init {
        getTasks()
        getIsShowTaskNotificationDate()
        getIsShowUpdateDialog()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getTasks() {
        viewModelScope.launch {
            sortFilter.flatMapLatest { sortFilter ->
                getAllTasksUseCase(sortFilter.first, sortFilter.second)
            }.collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(
                        list = it,
                        activeTasksCount = it.filter { task -> task.isActive }.size,
                        inactiveTasksCount = it.filter { task -> !task.isActive }.size,
                        selectedCount = it.filter { task -> task.isSelected }.size,
                        isListHasCollapsable = it.any { task -> task.isCollapsable },
                        sortByIndex = sortBy.first().ordinal,
                        currentFilterSelection = filterSelection.first()
                    )
                }
            }
        }
    }

    private fun getIsShowTaskNotificationDate() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_SHOW_TASK_NOTIFICATION_DATE, true).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isShowNotificationDate = it)
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

    fun setSortBy(sortBy: SortBy) {
        viewModelScope.launch { putIntPreferenceUseCase(SORT_TASKS_BY_KEY, sortBy.ordinal) }
    }

    fun setFilterSelection(selection: List<Boolean>) {
        viewModelScope.launch {
            putStringPreferenceUseCase(TASKS_FILTER_SELECTION, selection.toPreferenceString())
        }
    }

    fun deleteInactiveTasks() {
        viewModelScope.launch {
            deleteInactiveTasksUseCase()
        }
    }

    fun deleteSelectedTasks() {
        viewModelScope.launch {
            deleteSelectedTasksUseCase()
        }
    }

    fun switchTaskActivity(taskId: Long) {
        viewModelScope.launch {
            switchTaskActivityUseCase(taskId)
        }
    }

    fun switchIsSelectedNote(taskId: Long) {
        viewModelScope.launch {
            switchIsSelectedTaskUseCase(taskId)
        }
    }

    fun resetSelections(taskId: Long) {
        viewModelScope.launch {
            resetTasksSelectionsUseCase()
            switchIsSelectedTaskUseCase(taskId)
        }
    }

    fun selectAllTasks() {
        viewModelScope.launch {
            selectAllTasksUseCase()
        }
    }

    fun switchCollapse(taskId: Long) {
        viewModelScope.launch {
            switchTaskCollapseUseCase(taskId)
        }
    }

    fun expandAll() {
        viewModelScope.launch {
            expandAllTasksUseCase()
        }
    }

    fun collapseAll() {
        viewModelScope.launch {
            collapseAllTasksUseCase()
        }
    }
}
