package by.coolightman.notes.data.mappers

import by.coolightman.notes.data.local.dbModel.NotificationDb
import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.domain.model.RepeatType
import by.coolightman.notes.util.convertToCalendar

fun NotificationDb.toNotification(): Notification = Notification(
    id = id,
    taskId = taskId,
    time = time.convertToCalendar(),
    repeatType = RepeatType.values()[repeatType]
)

fun Notification.toNotificationDb(): NotificationDb = NotificationDb(
    id = id,
    taskId = taskId,
    time = time.timeInMillis,
    repeatType = repeatType.ordinal
)