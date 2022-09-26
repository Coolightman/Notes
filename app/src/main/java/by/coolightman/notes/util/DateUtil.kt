package by.coolightman.notes.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(): String {
    if (this == 0L) return ""
    val defPattern = "HH:mm | dd.MM.yy"
    val format = SimpleDateFormat(defPattern, Locale.getDefault())
    return format.format(Date(this))
}