package by.coolightman.notes.util

import java.util.Calendar

fun Calendar.isOld(): Boolean =
    Calendar.getInstance().timeInMillis > this.timeInMillis

fun Long.convertToCalendar(): Calendar = Calendar.getInstance().apply {
    timeInMillis = this@convertToCalendar
}

fun Long.isOld(): Boolean = Calendar.getInstance().timeInMillis > this

fun Calendar.roundTimeToMinute(): Calendar =
    this.apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
