package by.coolightman.notes.presenter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.usecase.tasks.CreateTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.GetTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.UpdateTaskUseCase
import by.coolightman.notes.presenter.state.EditTaskScreenState
import by.coolightman.notes.util.ARG_TASK_ID
import by.coolightman.notes.util.toFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {

    var state by mutableStateOf(EditTaskScreenState())
        private set

    private var task: Task? = null

    init {
        val taskId = savedStateHandle.get<Long>(ARG_TASK_ID) ?: 0L
        if (taskId != 0L) {
            getTask(taskId)
        }
    }

    private fun getTask(taskId: Long) {
        viewModelScope.launch {
            task = getTaskUseCase(taskId)
            task?.let {
                state = state.copy(
                    text = it.text,
                    color = it.color,
                    isImportant = it.isImportant,
                    date = it.createdAt.toFormattedDate()
                )
            }
        }
    }

    fun saveTask(
        text: String,
        color: Long,
        isImportant: Boolean
    ) {
        viewModelScope.launch {
            task?.let {
                val updatedTask = it.copy(
                    text = text,
                    color = color,
                    isImportant = isImportant,
                    isEdited = true,
                    editedAt = System.currentTimeMillis()
                )
                updateTaskUseCase(updatedTask)
                return@launch
            }

            val createdTask = Task(
                text = text,
                color = color,
                isImportant = isImportant,
                createdAt = System.currentTimeMillis(),
                editedAt = 0L,
                isEdited = false,
                isActive = false,
                isHidden = false
            )
            createTaskUseCase(createdTask)
        }
    }
}