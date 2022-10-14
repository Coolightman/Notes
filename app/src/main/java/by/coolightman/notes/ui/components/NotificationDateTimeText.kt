package by.coolightman.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import by.coolightman.notes.ui.theme.InactiveBackground
import by.coolightman.notes.util.toFormattedDate
import by.coolightman.notes.util.toFormattedTime

private const val CORNERS_ROUND = 8

@Composable
fun NotificationDateTimeText(
    notificationDate: Long,
    onClickTime: () -> Unit,
    onClickDate: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = notificationDate.toFormattedDate(),
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(CORNERS_ROUND.dp, 0.dp, 0.dp, CORNERS_ROUND.dp))
                .background(InactiveBackground.copy(0.3f))
                .clickable { onClickDate() }
                .padding(8.dp, 4.dp, 6.dp, 4.dp)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = notificationDate.toFormattedTime(),
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Light
            ),
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp, CORNERS_ROUND.dp, CORNERS_ROUND.dp, 0.dp))
                .background(InactiveBackground.copy(0.3f))
                .clickable { onClickTime() }
                .padding(6.dp, 4.dp, 8.dp, 4.dp)
        )
    }
}