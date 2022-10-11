package by.coolightman.notes.ui.screens.tasksScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetStringPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutStringPreferenceUseCase
import by.coolightman.notes.domain.usecase.tasks.*
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
    private val setIsSelectedTaskUseCase: SetIsSelectedTaskUseCase,
    private val selectAllTasksUseCase: SelectAllTasksUseCase,
    private val switchTaskExpandUseCase: SwitchTaskExpandUseCase,
    private val expandAllTasksUseCase: ExpandAllTasksUseCase,
    private val collapseAllTasksUseCase: CollapseAllTasksUseCase,
    private val putIntPreferenceUseCase: PutIntPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val putStringPreferenceUseCase: PutStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(TasksUiState())
        private set

    private val sortBy: Flow<SortBy> =
        getIntPreferenceUseCase(SORT_TASKS_BY_KEY)
            .map { value -> SortBy.values()[value] }

    private val filterSelection: Flow<List<Boolean>> =
        getStringPreferenceUseCase(TASKS_FILTER_SELECTION)
            .map { convertPrefStringToFilterSelectionList(it) }

    private val sortFilter: Flow<Pair<SortBy, List<Boolean>>> =
        sortBy.combine(filterSelection){ sort, filter -> Pair(sort, filter) }

    init {
        getTasks()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getTasks() {
        viewModelScope.launch {
            sortFilter.flatMapLatest { sortFilter ->
                getAllTasksUseCase(sortFilter.first, sortFilter.second)
            }.collectLatest {
                uiState = uiState.copy(
                    list = it,
                    activeTasksCount = it.filter { task -> task.isActive }.size,
                    inactiveTasksCount = it.filter { task -> !task.isActive }.size,
                    selectedCount = it.filter { task -> task.isSelected }.size,
                    isListHasExpandable = it.any { task -> task.isExpandable },
                    sortByIndex = sortBy.first().ordinal,
                    currentFilterSelection = filterSelection.first()
                )
            }
        }
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

    fun setIsSelectedNote(taskId: Long) {
        viewModelScope.launch {
            setIsSelectedTaskUseCase(taskId)
        }
    }

    fun resetSelections(taskId: Long) {
        viewModelScope.launch {
            resetTasksSelectionsUseCase()
            setIsSelectedTaskUseCase(taskId)
        }
    }

    fun selectAllTasks() {
        viewModelScope.launch {
            selectAllTasksUseCase()
        }
    }

    fun switchExpand(taskId: Long){
        viewModelScope.launch {
            switchTaskExpandUseCase(taskId)
        }
    }

    fun expandAll(){
        viewModelScope.launch {
            expandAllTasksUseCase()
        }
    }

    fun collapseAll(){
        viewModelScope.launch {
            collapseAllTasksUseCase()
        }
    }
}