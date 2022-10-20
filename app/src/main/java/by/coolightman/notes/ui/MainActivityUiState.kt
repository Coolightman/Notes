package by.coolightman.notes.ui

import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.ThemeMode

data class MainActivityUiState(
    val startDestinationPreference: String = NavRoutes.Notes.route,
    val themeModePreference: ThemeMode = ThemeMode.DARK_MODE
)
