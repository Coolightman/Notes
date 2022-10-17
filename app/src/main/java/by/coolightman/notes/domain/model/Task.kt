package by.coolightman.notes.domain.model

import java.util.Calendar

data class Task(
    val id: Long = 0,
    val text: String,
    val colorIndex: Int,
    val isImportant: Boolean,
    val createdAt: Long,
    val editedAt: Long,
    val isEdited: Boolean,
    val isActive: Boolean,
    val isHidden: Boolean,
    val isSelected: Boolean,
    val isCollapsable: Boolean,
    val isCollapsed: Boolean,
    val isHasNotification: Boolean,
    val notificationTime: Calendar
)
