package by.coolightman.notes.domain.model

data class Task(
    val id: Long = 0,
    val text: String,
    val colorIndex: Int,
    val isImportant: Boolean,
    val createdAt: Long,
    val editedAt: Long,
    val isEdited: Boolean,
    val isActive: Boolean,
    val isSelected: Boolean,
    val isCollapsable: Boolean,
    val isCollapsed: Boolean,
    val isHasNotification: Boolean,
    val notifications: List<Notification>
)
