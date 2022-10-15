package by.coolightman.notes.ui.screens.editTaskScreen

import java.util.*

data class EditTaskUiState(
    val text: String = "",
    val createdAt: String = "",
    val editedAt: String = "",
    val colorIndex: Int = 0,
    val isImportant: Boolean = false,
    val isHasNotification: Boolean = false,
    val notificationTime: Calendar = Calendar.getInstance(Locale.getDefault())
)