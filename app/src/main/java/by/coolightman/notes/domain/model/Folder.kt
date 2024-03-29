package by.coolightman.notes.domain.model

data class Folder(
    val id: Long = 0,
    val title: String,
    val colorIndex: Int,
    val createdAt: Long,
    val isInTrash: Boolean,
    val isPinned: Boolean,
    val isSelected: Boolean,
    val externalFolderId: Long
)
