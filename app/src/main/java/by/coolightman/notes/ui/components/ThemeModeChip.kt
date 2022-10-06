package by.coolightman.notes.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import by.coolightman.notes.ui.model.ThemeMode

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
        modifier = Modifier
            .height(30.dp)
            .width(100.dp)
    )
}