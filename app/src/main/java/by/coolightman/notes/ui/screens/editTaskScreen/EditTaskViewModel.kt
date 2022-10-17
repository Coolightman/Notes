package by.coolightman.notes.ui.screens.editTaskScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.tasks.CreateTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.DeleteTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.GetTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.UpdateTaskUseCase
import by.coolightman.notes.util.ARG_TASK_ID
import by.coolightman.notes.util.NEW_TASK_COLOR_KEY
import by.coolightman.notes.util.roundTimeToMinute
import by.coolightman.notes.util.toFormattedFullDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    var uiState by mutableStateOf(EditTaskUiState())
        private set

    private var task: Task? = null

    init {
        val taskId = savedStateHandle.get<Long>(ARG_TASK_ID) ?: 0L
        if (taskId != 0L) {
            getTask(taskId)
        } else {
            getNewTaskColorPreference()
        }
    }

    private fun getNewTaskColorPreference() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_TASK_COLOR_KEY).collectLatest {
                uiState = uiState.copy(
                    colorIndex = it
                )
            }
        }
    }

    private fun getTask(taskId: Long) {
        viewModelScope.launch {
            task = getTaskUseCase(taskId)
            task?.let {
                uiState = uiState.copy(
                    text = it.text,
                    colorIndex = it.colorIndex,
                    isImportant = it.isImportant,
                    createdAt = it.createdAt.toFormattedFullDate(),
                    editedAt = it.editedAt.toFormattedFullDate(),
                    isHasNotification = it.isHasNotification,
                    notificationTime = it.notificationTime
                )
            }
        }
    }

    fun saveTask(
        text: String,
        colorIndex: Int,
        isImportant: Boolean,
        numberOfLines: Int,
        isHasNotification: Boolean,
        notificationTime: Calendar
    ) {
        viewModelScope.launch {
            if (task == null) {
                val createdTask = Task(
                    text = text,
                    colorIndex = colorIndex,
                    isImportant = isImportant,
                    createdAt = System.currentTimeMillis(),
                    editedAt = 0L,
                    isEdited = false,
                    isActive = true,
                    isHidden = false,
                    isSelected = false,
                    isCollapsable = isCollapsable(numberOfLines),
                    isCollapsed = true,
                    isHasNotification = isHasNotification,
                    notificationTime = notificationTime.roundTimeToMinute()
                )
                createTaskUseCase(createdTask)
            } else {
                task?.let {
                    val updatedTask = it.copy(
                        text = text,
                        colorIndex = colorIndex,
                        isImportant = isImportant,
                        isEdited = true,
                        editedAt = System.currentTimeMillis(),
                        isCollapsable = isCollapsable(numberOfLines),
                        isCollapsed =
                        if (!isCollapsable(numberOfLines)) false
                        else it.isCollapsed,
                        isHasNotification = isHasNotification,
                        notificationTime = notificationTime.roundTimeToMinute()
                    )
                    updateTaskUseCase(updatedTask)
                }
            }
        }
    }

    private fun isCollapsable(numberOfLines: Int) = numberOfLines > 1

    fun deleteTask() {
        viewModelScope.launch {
            task?.let {
                deleteTaskUseCase(it.id)
            }
        }
    }
}