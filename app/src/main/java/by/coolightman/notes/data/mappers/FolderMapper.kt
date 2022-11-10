package by.coolightman.notes.data.mappers

import by.coolightman.notes.data.local.dbModel.FolderDb
import by.coolightman.notes.domain.model.Folder

fun FolderDb.toFolder(): Folder = Folder(
    id = id,
    title = title,
    colorIndex = colorIndex,
    createdAt = createdAt,
    isInTrash = isInTrash,
    isPinned = isPinned,
    externalFolderId = externalFolderId,
    isSelected = false
)

fun Folder.toFolderDb(): FolderDb = FolderDb(
    id = id,
    title = title,
    colorIndex = colorIndex,
    createdAt = createdAt,
    isInTrash = isInTrash,
    isPinned = isPinned,
    externalFolderId = externalFolderId
)
