package by.coolightman.notes.data.mappers

import by.coolightman.notes.data.local.dbModel.TaskDb
import by.coolightman.notes.domain.model.Task

fun TaskDb.toTask(): Task = Task(
    id = id,
    text = text,
    color = color,
    isImportant = isImportant,
    createdAt = createdAt,
    editedAt = editedAt,
    isEdited = isEdited,
    isActive = isActive,
    isHidden = isHidden
)

fun Task.toTaskDb(): TaskDb = TaskDb(
    id = id,
    text = text,
    color = color,
    isImportant = isImportant,
    createdAt = createdAt,
    editedAt = editedAt,
    isEdited = isEdited,
    isActive = isActive,
    isHidden = isHidden
)