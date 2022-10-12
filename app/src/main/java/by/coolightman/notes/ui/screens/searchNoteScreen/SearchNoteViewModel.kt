package by.coolightman.notes.ui.screens.searchNoteScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.notes.SearchNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val searchNotesUseCase: SearchNotesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SearchNoteUiState())
        private set

    fun searchByKey(key: String) {
        viewModelScope.launch {
            searchNotesUseCase(key).collectLatest {
                uiState = uiState.copy(
                    list = it
                )
            }
        }
    }

    fun clearSearchResult() {
        uiState = uiState.copy(
            list = emptyList()
        )
    }
}