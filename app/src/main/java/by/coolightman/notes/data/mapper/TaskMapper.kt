package by.coolightman.notes.data.mapper

import by.coolightman.notes.data.database.dbModel.TaskDb
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