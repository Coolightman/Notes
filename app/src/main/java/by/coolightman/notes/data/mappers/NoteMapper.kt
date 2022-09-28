package by.coolightman.notes.data.mappers

import by.coolightman.notes.data.local.dbModel.NoteDb
import by.coolightman.notes.domain.model.Note

fun NoteDb.toNote(): Note = Note(
    id = id,
    title = title,
    text = text,
    color = color,
    createdAt = createdAt,
    editedAt = editedAt,
    isShowDate = isShowDate,
    isEdited = isEdited,
    isInTrash = isInTrash
)

fun Note.toNoteDb(): NoteDb = NoteDb(
    id = id,
    title = title,
    text = text,
    color = color,
    createdAt = createdAt,
    editedAt = editedAt,
    isShowDate = isShowDate,
    isEdited = isEdited,
    isInTrash = isInTrash
)
