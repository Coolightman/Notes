package by.coolightman.notes.domain.model

data class Task(
    val id: Long,
    val text: String,
    val color: Long,
    val isImportant: Boolean,
    val createdAt: Long,
    val editedAt: Long,
    val isEdited: Boolean,
    val isActive: Boolean,
    val isHidden: Boolean
)
