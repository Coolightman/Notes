package by.coolightman.notes.util

import java.text.DateFormat
import java.util.*

fun Long.toFormattedDate(): String {
    if (this == 0L) return ""
    val format =
        DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())
    return format.format(Date(this))
}