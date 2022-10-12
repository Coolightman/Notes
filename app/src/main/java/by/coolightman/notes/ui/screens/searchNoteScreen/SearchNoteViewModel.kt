package by.coolightman.notes.ui.screens.searchNoteScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(

): ViewModel() {

    var uiState by mutableStateOf(SearchNoteUiState())
        private set
}