package by.coolightman.notes.ui.screens.tasksScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.tasks.*
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val collapseAllTasksUseCase: CollapseAllTasksUseCase
) : ViewModel() {

    var uiState by mutableStateOf(TasksUiState())
        private set

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            getAllTasksUseCase().collect {
                uiState = uiState.copy(
                    list = it,
                    activeTasksCount = it.filter { task -> task.isActive }.size,
                    inactiveTasksCount = it.filter { task -> !task.isActive }.size,
                    selectedCount = it.filter { task -> task.isSelected }.size,
                    isListHasExpandable = it.any { task -> task.isExpandable }
                )
            }
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