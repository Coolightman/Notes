package by.coolightman.notes.util

import java.text.DateFormat
import java.util.Date
import java.util.Locale

fun Long.toFormattedFullDate(): String {
    if (this == 0L) return ""
    val format =
        DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault())
    return format.format(Date(this))
}

fun Long.toFormattedTime(): String {
    if (this == 0L) return ""
    val format =
        DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())
    return format.format(Date(this))
}

fun Long.toFormattedDate(): String {
    if (this == 0L) return ""
    val format =
        DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
    return format.format(Date(this))
}
