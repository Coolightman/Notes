package by.coolightman.notes.ui

import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.ThemeMode

data class MainActivityUiState(
    val startDestinationPreference: String = NavRoutes.Splash.route,
    val themeModePreference: ThemeMode = ThemeMode.SYSTEM_MODE
)