package by.coolightman.notes.domain.model

data class Note(
    val id: Long = 0,
    val title: String,
    val text: String,
    val colorIndex: Int,
    val createdAt: Long,
    val editedAt: Long,
    val isShowDate: Boolean,
    val isEdited: Boolean,
    val isInTrash: Boolean,
    val isSelected: Boolean,
    val isCollapsable: Boolean,
    val isCollapsed: Boolean,
    val isPinned: Boolean,
    val folderId: Long
)
