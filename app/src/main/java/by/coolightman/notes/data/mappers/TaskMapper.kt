package by.coolightman.notes.data.mappers

import by.coolightman.notes.data.local.dbModel.TaskDb
import by.coolightman.notes.data.local.dbModel.relations.TaskWithNotificationsDb
import by.coolightman.notes.domain.model.Task

fun TaskWithNotificationsDb.toTask(): Task = Task(
    id = taskDb.id,
    text = taskDb.text,
    colorIndex = taskDb.colorIndex,
    isImportant = taskDb.isImportant,
    createdAt = taskDb.createdAt,
    editedAt = taskDb.editedAt,
    isEdited = taskDb.isEdited,
    isActive = taskDb.isActive,
    isSelected = false,
    isCollapsable = taskDb.isCollapsable,
    isCollapsed = taskDb.isCollapsed,
    notifications = notificationsDb.map { it.toNotification() }
)

fun Task.toTaskDb(): TaskDb = TaskDb(
    id = id,
    text = text,
    colorIndex = colorIndex,
    isImportant = isImportant,
    createdAt = createdAt,
    editedAt = editedAt,
    isEdited = isEdited,
    isActive = isActive,
    isCollapsable = isCollapsable,
    isCollapsed = isCollapsed
)
