package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.coolightman.notes.ui.theme.InactiveBackground
import by.coolightman.notes.ui.theme.NotesTheme
import by.coolightman.notes.util.toFormattedDate
import by.coolightman.notes.util.toFormattedTime
import java.util.Calendar
import java.util.Locale

private val CORNER_SHAPE = RoundedCornerShape(4.dp)

@Composable
fun NotificationDateTimeText(
    notificationDate: Long,
    onClickTime: () -> Unit,
    onClickDate: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        DateTimeText(
            text = notificationDate.toFormattedDate(),
            onClick = { onClickDate() }
        )

        Spacer(modifier = Modifier.width(8.dp))

        DateTimeText(
            text = notificationDate.toFormattedTime(),
            onClick = { onClickTime() }
        )
    }
}

@Composable
fun DateTimeText(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6.copy(
            fontWeight = FontWeight.Light
        ),
        modifier = Modifier
            .clip(CORNER_SHAPE)
            .background(InactiveBackground.copy(0.2f))
            .clickable { onClick() }
            .border(1.dp, color = MaterialTheme.colors.primary.copy(0.8f), shape = CORNER_SHAPE)
            .padding(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun NotificationDateTimeTextPreview() {
    NotesTheme {
        NotificationDateTimeText(
            notificationDate = Calendar.getInstance(Locale.getDefault()).timeInMillis,
            onClickTime = { },
            onClickDate = { }
        )
    }
}
