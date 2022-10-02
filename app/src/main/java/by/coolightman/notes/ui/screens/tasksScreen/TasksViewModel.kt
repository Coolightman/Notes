package by.coolightman.notes.ui.screens.tasksScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.tasks.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val deleteInactiveTasksUseCase: DeleteInactiveTasksUseCase,
    private val switchTaskActivityUseCase: SwitchTaskActivityUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val showTaskUseCase: ShowTaskUseCase
) : ViewModel() {

    var uiState by mutableStateOf(TasksUiState())
        private set

    private lateinit var canceledJob: Job

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            getAllTasksUseCase().collect {
                uiState = uiState.copy(list = it)
            }
        }
    }

    fun deleteInactiveTasks() {
        canceledJob = viewModelScope.launch {
            deleteInactiveTasksUseCase()
        }
    }

    fun deleteTask(taskId: Long) {
        canceledJob = viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }

    fun cancelDeletion(taskId: Long) {
        canceledJob.cancel()
        viewModelScope.launch {
            showTaskUseCase(taskId)
        }
    }

    fun switchTaskActivity(taskId: Long) {
        viewModelScope.launch {
            switchTaskActivityUseCase(taskId)
        }
    }
}