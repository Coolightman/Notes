package by.coolightman.notes.ui.screens.settingsScreen

import by.coolightman.notes.ui.model.ThemeMode

data class SettingsUiState(
    val appStartDestination: String = "",
    val themeMode: ThemeMode = ThemeMode.SYSTEM_MODE
)
