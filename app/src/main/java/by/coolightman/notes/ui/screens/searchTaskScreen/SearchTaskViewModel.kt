package by.coolightman.notes.ui.screens.searchTaskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.usecase.tasks.SearchTaskUseCase
import by.coolightman.notes.domain.usecase.tasks.SwitchTaskActivityUseCase
import by.coolightman.notes.domain.usecase.tasks.SwitchTaskCollapseUseCase
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
class SearchTaskViewModel @Inject constructor(
    private val searchTaskUseCase: SearchTaskUseCase,
    private val switchTaskActivityUseCase: SwitchTaskActivityUseCase,
    private val switchTaskCollapseUseCase: SwitchTaskCollapseUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchTaskUiState())
    val uiState: StateFlow<SearchTaskUiState> = _uiState.asStateFlow()

    private val _searchKey = MutableStateFlow("")

    init {
        search()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun search() {
        viewModelScope.launch {
            _searchKey.flatMapLatest { key ->
                searchTaskUseCase(key)
            }.collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(list = it)
                }
            }
        }
    }

    fun switchTaskActivity(taskId: Long) {
        viewModelScope.launch {
            switchTaskActivityUseCase(taskId)
        }
    }

    fun switchCollapse(taskId: Long) {
        viewModelScope.launch {
            switchTaskCollapseUseCase(taskId)
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
