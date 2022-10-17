package by.coolightman.notes.ui.screens.searchNoteScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.notes.SearchNoteUseCase
import by.coolightman.notes.domain.usecase.notes.SwitchNoteCollapseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val searchNoteUseCase: SearchNoteUseCase,
    private val switchNoteCollapseUseCase: SwitchNoteCollapseUseCase
) : ViewModel() {

    var uiState by mutableStateOf(SearchNoteUiState())
        private set

    private val _searchKey = MutableStateFlow("")

    init {
        search()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun search() {
        viewModelScope.launch {
            _searchKey.flatMapLatest { key ->
                searchNoteUseCase(key)
            }.collectLatest {
                uiState = uiState.copy(
                    list = it
                )
            }
        }
    }

    fun switchCollapse(noteId: Long){
        viewModelScope.launch {
            switchNoteCollapseUseCase(noteId)
        }
    }

    fun setSearchKey(key: String) {
        viewModelScope.launch { _searchKey.emit(key) }
    }

    fun clearSearchResult() {
        uiState = uiState.copy(
            list = emptyList()
        )
    }
}