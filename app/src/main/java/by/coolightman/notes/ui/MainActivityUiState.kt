package by.coolightman.notes.ui

import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.ThemeMode

data class MainActivityUiState(
    val startDestination: String = NavRoutes.Notes.route,
    val themeMode: ThemeMode = ThemeMode.SYSTEM_MODE
)