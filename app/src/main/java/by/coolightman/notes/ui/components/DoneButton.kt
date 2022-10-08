package by.coolightman.notes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoneButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
        color = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 6.dp
    ) {
        Box(modifier = Modifier
            .size(56.dp)
            .clickable { onClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "done",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}