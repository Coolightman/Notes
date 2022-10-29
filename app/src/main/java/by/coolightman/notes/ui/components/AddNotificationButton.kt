package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import by.coolightman.notes.R

@Composable
fun AddNotificationButton(
    modifier: Modifier = Modifier,
    onClickAdd: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(
            shape = CircleShape,
            onClick = { onClickAdd() }
        ) {
            Text(
                text = stringResource(R.string.add_notification),
                color = MaterialTheme.colors.onSurface
            )
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "add notification",
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
