package by.coolightman.notes.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.preferences.GetStringPreferenceUseCase
import by.coolightman.notes.util.START_DESTINATION_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getStringPreferenceUseCase: GetStringPreferenceUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MainActivityUiState())
        private set

    init {
        getStartDestination()
    }

    private fun getStartDestination() {
        viewModelScope.launch {
            val destination = getStringPreferenceUseCase(START_DESTINATION_KEY).first()
            uiState = uiState.copy(
                startDestination = destination
            )
        }
    }

}