package by.coolightman.notes.data.mappers

import by.coolightman.notes.data.local.dbModel.NoteDb
import by.coolightman.notes.domain.model.Note

fun NoteDb.toNote(): Note = Note(
    id = id,
    title = title,
    text = text,
    colorIndex = colorIndex,
    createdAt = createdAt,
    editedAt = editedAt,
    isShowDate = isShowDate,
    isEdited = isEdited,
    isInTrash = isInTrash,
    isSelected = isSelected,
    isExpandable = isExpandable,
    isExpanded = isExpanded
)

fun Note.toNoteDb(): NoteDb = NoteDb(
    id = id,
    title = title,
    text = text,
    colorIndex = colorIndex,
    createdAt = createdAt,
    editedAt = editedAt,
    isShowDate = isShowDate,
    isEdited = isEdited,
    isInTrash = isInTrash,
    isSelected = isSelected,
    isExpandable = isExpandable,
    isExpanded = isExpanded
)
