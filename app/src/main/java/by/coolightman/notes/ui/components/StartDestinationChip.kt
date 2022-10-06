package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartDestinationChip(
    icon: Painter,
    appStartDestination: String,
    chipDestination: String,
    title: String,
    onClick: (String) -> Unit
) {
    FilterChip(
        selected = appStartDestination == chipDestination,
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = ""
            )
        },
        onClick = {
            if (appStartDestination != chipDestination) {
                onClick(chipDestination)
            }
        },
        content = { Text(text = title) },
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = MaterialTheme.colors.secondary.copy(0.8f),
            selectedLeadingIconColor = MaterialTheme.colors.onSurface,
            selectedContentColor = MaterialTheme.colors.onSurface
        ),
        modifier = Modifier
            .height(40.dp)
            .width(100.dp)
    )
}