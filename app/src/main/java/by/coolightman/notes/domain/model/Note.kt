package by.coolightman.notes.domain.model

data class Note(
    val id: Long,
    val title: String,
    val text: String,
    val color: Long,
    val createdAt: Long,
    val editedAt: Long,
    val isShowDate: Boolean,
    val isEdited: Boolean,
    val isInTrash: Boolean
)
