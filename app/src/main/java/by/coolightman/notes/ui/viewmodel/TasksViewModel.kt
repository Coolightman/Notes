package by.coolightman.notes.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.tasks.DeleteInactiveTasksUseCase
import by.coolightman.notes.domain.usecase.tasks.DeleteTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.GetAllTasksUseCase
import by.coolightman.notes.domain.usecase.tasks.SwitchTaskActivityUseCase
import by.coolightman.notes.ui.state.TasksScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val deleteInactiveTasksUseCase: DeleteInactiveTasksUseCase,
    private val switchTaskActivityUseCase: SwitchTaskActivityUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    var state by mutableStateOf(TasksScreenState())
        private set

    private lateinit var deleteTaskJob: Job

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            getAllTasksUseCase().collect {
                state = state.copy(
                    list = it
                )
            }
        }
    }

    fun deleteInactiveTasks() {
        viewModelScope.launch {
            deleteInactiveTasksUseCase()
        }
    }

    fun deleteTask(taskId: Long) {
        deleteTaskJob = viewModelScope.launch {
            deleteTaskUseCase(taskId)
        }
    }

    fun cancelTaskDeletion() {
        deleteTaskJob.cancel()
    }

    fun switchTaskActivity(taskId: Long) {
        viewModelScope.launch {
            switchTaskActivityUseCase(taskId)
        }
    }

}