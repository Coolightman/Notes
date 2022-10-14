package by.coolightman.notes.ui.components

import android.app.TimePickerDialog
import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun TimePicker(
    calendar: Calendar,
    onCancel: () -> Unit,
    selectedTime: (Calendar) -> Unit
) {
    val context = LocalContext.current

    TimePickerDialog(
        context,
        { _, hour: Int, minute: Int ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            selectedTime(calendar)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        DateFormat.is24HourFormat(context)
    ).apply {
        show()
        setOnCancelListener { onCancel() }
        setOnDismissListener { onCancel() }
    }
}