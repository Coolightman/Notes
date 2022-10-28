package by.coolightman.notes.domain.model

import java.util.Calendar

data class Notification(
    val id: Int = 0,
    val taskId: Long,
    val time: Calendar,
    val repeatType: RepeatType
)