package by.coolightman.notes.ui.screens.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.preferences.GetBooleanPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetStringPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutBooleanPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutStringPreferenceUseCase
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.NotesViewMode
import by.coolightman.notes.ui.model.ThemeMode
import by.coolightman.notes.util.IS_NOTES_COLORED_BACK
import by.coolightman.notes.util.IS_SHOW_NOTE_DATE
import by.coolightman.notes.util.IS_SHOW_TASK_NOTIFICATION_DATE
import by.coolightman.notes.util.NEW_NOTE_COLOR_KEY
import by.coolightman.notes.util.NEW_TASK_COLOR_KEY
import by.coolightman.notes.util.NOTES_VIEW_MODE
import by.coolightman.notes.util.START_DESTINATION_KEY
import by.coolightman.notes.util.THEME_MODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val putStringPreferenceUseCase: PutStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
    private val putIntPreferenceUseCase: PutIntPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val putBooleanPreferenceUseCase: PutBooleanPreferenceUseCase,
    private val getBooleanPreferenceUseCase: GetBooleanPreferenceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        getSettingsData()
    }

    private fun getSettingsData() {
        getStartDestination()
        getThemeMode()
        getNewNoteColor()
        getNewTaskColor()
        getIsShowNotesDate()
        getIsNotesColoredBackground()
        getNotesViewMode()
        getIsShowTaskNotificationDate()
    }

    private fun getIsShowTaskNotificationDate() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_SHOW_TASK_NOTIFICATION_DATE, true).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isShowTaskNotificationDate = it)
                }
            }
        }
    }

    private fun getIsShowNotesDate() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_SHOW_NOTE_DATE, true).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isShowNotesDate = it)
                }
            }
        }
    }

    private fun getIsNotesColoredBackground() {
        viewModelScope.launch {
            getBooleanPreferenceUseCase(IS_NOTES_COLORED_BACK, true).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(isNotesColoredBackground = it)
                }
            }
        }
    }

    private fun getThemeMode() {
        viewModelScope.launch {
            getIntPreferenceUseCase(THEME_MODE_KEY, ThemeMode.DARK_MODE.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(themeMode = ThemeMode.values()[it])
                }
            }
        }
    }

    private fun getStartDestination() {
        viewModelScope.launch {
            getStringPreferenceUseCase(START_DESTINATION_KEY, NavRoutes.Notes.route).collectLatest {
                if (it.isNotEmpty()) {
                    _uiState.update { currentState ->
                        currentState.copy(appStartDestination = it)
                    }
                }
            }
        }
    }

    private fun getNewNoteColor() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_NOTE_COLOR_KEY, ItemColor.GRAY.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(newNoteColorIndex = it)
                }
            }
        }
    }

    private fun getNewTaskColor() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_TASK_COLOR_KEY, ItemColor.GRAY.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(newTaskColorIndex = it)
                }
            }
        }
    }

    private fun getNotesViewMode() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NOTES_VIEW_MODE, NotesViewMode.LIST.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(currentNotesViewMode = NotesViewMode.values()[it])
                }
            }
        }
    }

    fun setStartDestination(route: String) {
        viewModelScope.launch {
            putStringPreferenceUseCase(START_DESTINATION_KEY, route)
        }
    }

    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            putIntPreferenceUseCase(THEME_MODE_KEY, themeMode.ordinal)
        }
    }

    fun setNewNoteColor(colorIndex: Int) {
        viewModelScope.launch {
            putIntPreferenceUseCase(NEW_NOTE_COLOR_KEY, colorIndex)
        }
    }

    fun setNewTaskColor(colorIndex: Int) {
        viewModelScope.launch {
            putIntPreferenceUseCase(NEW_TASK_COLOR_KEY, colorIndex)
        }
    }

    fun setIsShowNotedDate(value: Boolean) {
        viewModelScope.launch {
            putBooleanPreferenceUseCase(IS_SHOW_NOTE_DATE, value)
        }
    }

    fun setIsShowTaskNotificationDate(value: Boolean) {
        viewModelScope.launch {
            putBooleanPreferenceUseCase(IS_SHOW_TASK_NOTIFICATION_DATE, value)
        }
    }

    fun setIsNotesColoredBackground(value: Boolean) {
        viewModelScope.launch {
            putBooleanPreferenceUseCase(IS_NOTES_COLORED_BACK, value)
        }
    }

    fun setNotesViewMode(value: NotesViewMode) {
        viewModelScope.launch {
            putIntPreferenceUseCase(NOTES_VIEW_MODE, value.ordinal)
        }
    }
}
