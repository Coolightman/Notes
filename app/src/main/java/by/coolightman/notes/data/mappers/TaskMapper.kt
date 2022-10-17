package by.coolightman.notes.data.mappers

import by.coolightman.notes.data.local.dbModel.TaskDb
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.util.convertToCalendar

fun TaskDb.toTask(): Task = Task(
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
    isCollapsed = isCollapsed,
    isHasNotification = isHasNotification,
    notificationTime = notificationTime.convertToCalendar(),
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
    isCollapsed = isCollapsed,
    isHasNotification = isHasNotification,
    notificationTime = notificationTime.timeInMillis
)
