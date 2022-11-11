package by.coolightman.notes.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import by.coolightman.notes.ui.theme.NightAccent

@Composable
fun isDarkMode(): Boolean = MaterialTheme.colors.secondary == NightAccent

@Composable
fun dropDownItemColor(): Color {
    return if (isDarkMode()) {
        MaterialTheme.colors.onSurface.copy(0.8f)
    } else {
        MaterialTheme.colors.onSurface.copy(0.6f)
    }
}
