package by.coolightman.notes.ui.components

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DatePicker(
    calendar: Calendar,
    onCancel: () -> Unit,
    selectedTime: (Calendar) -> Unit
) {
    val context = LocalContext.current

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedTime(calendar)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        show()
        setOnCancelListener { onCancel() }
        setOnDismissListener { onCancel() }
        datePicker.minDate = System.currentTimeMillis()
    }
}