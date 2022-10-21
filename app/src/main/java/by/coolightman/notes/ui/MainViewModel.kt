package by.coolightman.notes.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.GetStringPreferenceUseCase
import by.coolightman.notes.ui.model.NavRoutes
import by.coolightman.notes.ui.model.ThemeMode
import by.coolightman.notes.util.START_DESTINATION_KEY
import by.coolightman.notes.util.THEME_MODE_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MainActivityUiState())
        private set

    init {
        getStartDestinationPreference()
        getThemeModePreference()
    }

    private fun getStartDestinationPreference() {
        viewModelScope.launch {
            val destination =
                getStringPreferenceUseCase(START_DESTINATION_KEY, NavRoutes.Notes.route).first()
            uiState = uiState.copy(
                startDestinationPreference = destination
            )
        }
    }

    private fun getThemeModePreference() {
        viewModelScope.launch {
            getIntPreferenceUseCase(THEME_MODE_KEY, ThemeMode.DARK_MODE.ordinal).collectLatest {
                uiState = uiState.copy(
                    themeModePreference = ThemeMode.values()[it]
                )
            }
        }
    }
}
