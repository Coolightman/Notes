package by.coolightman.notes.ui.screens.settingsScreen

import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.NotesViewMode
import by.coolightman.notes.ui.model.ThemeMode

data class SettingsUiState(
    val appStartDestination: String = NavRoutes.Notes.route,
    val themeMode: ThemeMode = ThemeMode.SYSTEM_MODE,
    val newNoteColorIndex: Int = 0,
    val newTaskColorIndex: Int = 0,
    val isShowNotesDate: Boolean = false,
    val isNotesColoredBackground: Boolean = false,
    val isShowTaskNotificationDate: Boolean = false,
    val currentNotesViewMode: NotesViewMode = NotesViewMode.LIST
)
