package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R

@Composable
fun DoneNotificationButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(12.dp)
            .height(40.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = { onClick() },
        content = {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_notification_24),
                contentDescription = "done",
                tint = MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
            )
        }
    )
}
