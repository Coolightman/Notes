package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DoneButton(
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
                imageVector = Icons.Default.Done,
                contentDescription = "done",
                tint = MaterialTheme.colors.onSurface.copy(LocalContentAlpha.current)
            )
        }
    )
}
