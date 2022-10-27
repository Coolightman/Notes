package by.coolightman.notes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetStringPreferenceUseCase
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.ThemeMode
import by.coolightman.notes.util.START_DESTINATION_KEY
import by.coolightman.notes.util.THEME_MODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainActivityUiState())
    val uiState: StateFlow<MainActivityUiState> = _uiState.asStateFlow()

    init {
        getStartDestinationPreference()
        getThemeModePreference()
    }

    private fun getStartDestinationPreference() {
        viewModelScope.launch {
            val destination =
                getStringPreferenceUseCase(START_DESTINATION_KEY, NavRoutes.Notes.route).first()
            _uiState.update { currentState ->
                currentState.copy(startDestinationPreference = destination)
            }
        }
    }

    private fun getThemeModePreference() {
        viewModelScope.launch {
            getIntPreferenceUseCase(THEME_MODE_KEY, ThemeMode.DARK_MODE.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(themeModePreference = ThemeMode.values()[it])
                }
            }
        }
    }
}
