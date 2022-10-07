package by.coolightman.notes.ui.screens.settingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetStringPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutStringPreferenceUseCase
import by.coolightman.notes.ui.model.ThemeMode
import by.coolightman.notes.util.NEW_NOTE_COLOR_KEY
import by.coolightman.notes.util.NEW_TASK_COLOR_KEY
import by.coolightman.notes.util.START_DESTINATION_KEY
import by.coolightman.notes.util.THEME_MODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val putStringPreferenceUseCase: PutStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
    private val putIntPreferenceUseCase: PutIntPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    init {
        getSettingsData()
    }

    private fun getSettingsData() {
        getStartDestination()
        getThemeMode()
        getNewNoteColor()
        getNewTaskColor()
    }

    private fun getThemeMode() {
        viewModelScope.launch {
            getIntPreferenceUseCase(THEME_MODE_KEY).collectLatest {
                uiState = uiState.copy(
                    themeMode = ThemeMode.values()[it]
                )
            }
        }
    }

    private fun getStartDestination() {
        viewModelScope.launch {
            getStringPreferenceUseCase(START_DESTINATION_KEY).collectLatest {
                uiState = uiState.copy(
                    appStartDestination = it
                )
            }
        }
    }

    private fun getNewNoteColor() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_NOTE_COLOR_KEY).collectLatest {
                uiState = uiState.copy(
                    newNoteColorIndex = it
                )
            }
        }
    }

    private fun getNewTaskColor() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_TASK_COLOR_KEY).collectLatest {
                uiState = uiState.copy(
                    newTaskColorIndex = it
                )
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
}