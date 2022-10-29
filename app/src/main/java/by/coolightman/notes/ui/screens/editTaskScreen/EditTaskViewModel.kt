package by.coolightman.notes.ui.screens.editTaskScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.domain.model.RepeatType
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.usecase.preferences.GetBooleanPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.notifications.CreateNotificationUseCase
import by.coolightman.notes.domain.usecase.notifications.DeleteAllNotificationsByTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.CreateTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.DeleteTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.GetTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.UpdateTaskUseCase
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.util.ARG_TASK_ID
import by.coolightman.notes.util.IS_SHOW_TASK_NOTIFICATION_DATE
import by.coolightman.notes.util.NEW_TASK_COLOR_KEY
import by.coolightman.notes.util.roundTimeToMinute
import by.coolightman.notes.util.toFormattedFullDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getTaskUseCase: GetTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase,
    private val createNotificationUseCase: CreateNotificationUseCase,
    private val deleteAllNotificationsByTaskUseCase: DeleteAllNotificationsByTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditTaskUiState())
    val uiState: StateFlow<EditTaskUiState> = _uiState.asStateFlow()

    private var task: Task? = null

    init {
        val taskId = savedStateHandle.get<Long>(ARG_TASK_ID) ?: 0L
        if (taskId != 0L) {
            getTask(taskId)
        } else {
            getNewTaskColorPreference()
        }
        getIsShowTaskNotificationDate()
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

    private fun getNewTaskColorPreference() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_TASK_COLOR_KEY, ItemColor.GRAY.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(colorIndex = it)
                }
            }
        }
    }

    private fun getTask(taskId: Long) {
        viewModelScope.launch {
            task = getTaskUseCase(taskId)
            task?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        text = it.text,
                        colorIndex = it.colorIndex,
                        isImportant = it.isImportant,
                        createdAt = it.createdAt.toFormattedFullDate(),
                        editedAt = it.editedAt.toFormattedFullDate(),
                        isHasNotification = it.notifications.isNotEmpty(),
                        notifications = it.notifications.sortedBy { it.time }
                    )
                }
            }
        }
    }

    fun saveTask(
        text: String,
        colorIndex: Int,
        isImportant: Boolean,
        numberOfLines: Int,
        isHasNotification: Boolean
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
                    isSelected = false,
                    isCollapsable = isCollapsable(numberOfLines),
                    isCollapsed = true,
                    notifications = emptyList()
                )
                val taskId = createTaskUseCase(createdTask)

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
                        notifications = emptyList()
                    )
                    updateTaskUseCase(updatedTask)
                    if (it.notifications.isNotEmpty() && !isHasNotification){
                        deleteAllNotificationsByTaskUseCase(it.id)
                    }
                }
            }
        }
    }

    fun createNotification(taskId: Long, time: Calendar){
        viewModelScope.launch {
            createNotificationUseCase(
                Notification(
                    taskId = taskId,
                    time = time,
                    repeatType = RepeatType.NO
                )
            )
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
