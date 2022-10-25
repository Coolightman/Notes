package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import by.coolightman.notes.ui.model.ThemeMode
import by.coolightman.notes.ui.theme.InactiveBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ThemeModeChip(
    icon: Painter,
    appThemeMode: ThemeMode,
    chipThemeMode: ThemeMode,
    title: String,
    onClick: (ThemeMode) -> Unit
) {
    FilterChip(
        selected = appThemeMode == chipThemeMode,
        leadingIcon = {
            Icon(
                painter = icon,
                contentDescription = ""
            )
        },
        onClick = {
            if (appThemeMode != chipThemeMode) {
                onClick(chipThemeMode)
            }
        },
        content = { Text(text = title) },
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = MaterialTheme.colors.primary.copy(0.7f),
            selectedLeadingIconColor = MaterialTheme.colors.onSurface,
            selectedContentColor = MaterialTheme.colors.onSurface,
            backgroundColor = InactiveBackground.copy(0.3f)
        ),
        modifier = Modifier
            .height(30.dp)
            .width(100.dp)
    )
}
