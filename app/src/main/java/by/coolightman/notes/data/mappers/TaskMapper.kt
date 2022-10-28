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
    isSelected = taskDb.isSelected,
    isCollapsable = taskDb.isCollapsable,
    isCollapsed = taskDb.isCollapsed,
    isHasNotification = notificationsDb.isNotEmpty(),
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
    isSelected = isSelected,
    isCollapsable = isCollapsable,
    isCollapsed = isCollapsed
)

fun Task.toTaskWithNotificationsDb(): TaskWithNotificationsDb = TaskWithNotificationsDb(
    taskDb = this.toTaskDb(),
    notificationsDb = notifications.map { it.toNotificationDb() }
)
