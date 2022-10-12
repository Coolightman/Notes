package by.coolightman.notes.ui.screens.searchNoteScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import by.coolightman.notes.domain.usecase.notes.SearchNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val searchNotesUseCase: SearchNotesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SearchNoteUiState())
        private set

    fun searchByKey(key: String) {

    }

    fun clearSearchResult() {

    }
}