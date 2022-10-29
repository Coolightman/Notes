package by.coolightman.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.coolightman.notes.domain.model.Notification
import by.coolightman.notes.util.toFormattedFullDate

@Composable
fun TaskNotificationDate(
    modifier: Modifier = Modifier,
    isHasNotification: Boolean,
    notifications: List<Notification>
) {
    AnimatedVisibility(
        visible = isHasNotification,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                if (isHasNotification) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "notifications",
                        tint = MaterialTheme.colors.onSurface.copy(0.5f),
                        modifier = Modifier.padding(1.dp).size(12.dp)
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = notifications[0].time.timeInMillis.toFormattedFullDate(),
                        style = MaterialTheme.typography.caption.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Light
                        ),
                        color = MaterialTheme.colors.onSurface.copy(0.5f)
                    )
                }
            }
            Spacer(modifier = Modifier.width(24.dp))
        }
    }
}