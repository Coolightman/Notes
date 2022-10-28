package by.coolightman.notes.ui.screens.editTaskScreen

import by.coolightman.notes.domain.model.Notification

data class EditTaskUiState(
    val text: String = "",
    val createdAt: String = "",
    val editedAt: String = "",
    val colorIndex: Int = 0,
    val isImportant: Boolean = false,
    val isHasNotification: Boolean = false,
    val isShowNotificationDate: Boolean = false,
    val notifications: List<Notification> = emptyList()
)
