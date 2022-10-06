package by.coolightman.notes.ui.screens.settingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.preferences.GetStringPreferenceUseCase
import by.coolightman.notes.domain.usecase.preferences.PutStringPreferenceUseCase
import by.coolightman.notes.util.START_DESTINATION_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val putStringPreferenceUseCase: PutStringPreferenceUseCase,
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SettingsUiState())
        private set

    init {
        getSettingsData()
    }

    private fun getSettingsData() {
        viewModelScope.launch {
            getStringPreferenceUseCase(START_DESTINATION_KEY).collectLatest {
                uiState = uiState.copy(
                    appStartDestination = it
                )
            }
        }
    }

    fun setStartDestination(route: String) {
        viewModelScope.launch {
            putStringPreferenceUseCase(START_DESTINATION_KEY, route)
        }
    }
}