package by.coolightman.notes.data.mappers

import by.coolightman.notes.data.local.dbModel.TaskDb
import by.coolightman.notes.domain.model.Task
import java.util.*

fun TaskDb.toTask(): Task = Task(
    id = id,
    text = text,
    colorIndex = colorIndex,
    isImportant = isImportant,
    createdAt = createdAt,
    editedAt = editedAt,
    isEdited = isEdited,
    isActive = isActive,
    isHidden = isHidden,
    isSelected = isSelected,
    isExpandable = isExpandable,
    isExpanded = isExpanded,
    isHasNotification =
    if (isNotificationMade(notificationTime)) false
    else isHasNotification,
    notificationTime =
    if (isNotificationMade(notificationTime)) Calendar.getInstance(Locale.getDefault())
    else notificationTime.convertToCalendar(),
)

fun isNotificationMade(notificationTime: Long): Boolean {
    return Calendar.getInstance().timeInMillis > notificationTime
}

fun Task.toTaskDb(): TaskDb = TaskDb(
    id = id,
    text = text,
    colorIndex = colorIndex,
    isImportant = isImportant,
    createdAt = createdAt,
    editedAt = editedAt,
    isEdited = isEdited,
    isActive = isActive,
    isHidden = isHidden,
    isSelected = isSelected,
    isExpandable = isExpandable,
    isExpanded = isExpanded,
    isHasNotification = isHasNotification,
    notificationTime = notificationTime.timeInMillis
)

fun Long.convertToCalendar(): Calendar = Calendar.getInstance().apply {
    timeInMillis = this@convertToCalendar
}