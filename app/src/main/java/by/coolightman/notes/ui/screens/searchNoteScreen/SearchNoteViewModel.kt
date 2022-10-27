package by.coolightman.notes.ui.screens.searchNoteScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.notes.SearchNoteUseCase
import by.coolightman.notes.domain.usecase.notes.SwitchNoteCollapseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val searchNoteUseCase: SearchNoteUseCase,
    private val switchNoteCollapseUseCase: SwitchNoteCollapseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchNoteUiState())
    val uiState: StateFlow<SearchNoteUiState> = _uiState.asStateFlow()

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
                _uiState.update { currentState ->
                    currentState.copy(list = it)
                }
            }
        }
    }

    fun switchCollapse(noteId: Long) {
        viewModelScope.launch {
            switchNoteCollapseUseCase(noteId)
        }
    }

    fun setSearchKey(key: String) {
        viewModelScope.launch { _searchKey.emit(key) }
    }

    fun clearSearchResult() {
        _uiState.update { currentState ->
            currentState.copy(list = emptyList())
        }
    }
}
