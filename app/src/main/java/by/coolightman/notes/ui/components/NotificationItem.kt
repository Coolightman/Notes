package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.R
import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.domain.model.RepeatType
import by.coolightman.notes.util.convertToCalendar
import by.coolightman.notes.util.toFormattedFullDate

@Composable
fun NotificationItem(
    notification: Notification,
    onDelete: (Notification) -> Unit
) {
    Card(
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 24.dp, vertical = 3.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notification.time.timeInMillis.toFormattedFullDate(),
                    modifier = Modifier.padding(start = 15.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (notification.repeatType != RepeatType.NO) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_repeat_24),
                        contentDescription = "repeat type",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(notification.repeatType.shortText),
                        fontSize = 12.sp
                    )
                }
            }
            IconButton(
                onClick = { onDelete(notification) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "delete notification",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    val notification = Notification(
        time = System.currentTimeMillis().convertToCalendar(),
        repeatType = RepeatType.MONTH,
        taskId = 12
    )
    NotificationItem(notification = notification, onDelete = {})
}