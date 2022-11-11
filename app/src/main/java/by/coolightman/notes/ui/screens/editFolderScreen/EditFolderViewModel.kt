package by.coolightman.notes.ui.screens.editFolderScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.domain.usecase.folders.CreateFolderUseCase
import by.coolightman.notes.domain.usecase.folders.GetFolderUseCase
import by.coolightman.notes.domain.usecase.folders.PutFolderInTrashUseCase
import by.coolightman.notes.domain.usecase.folders.UpdateFolderUseCase
import by.coolightman.notes.domain.usecase.preferences.GetIntPreferenceUseCase
import by.coolightman.notes.ui.model.ItemColor
import by.coolightman.notes.util.ARG_EXTERNAL_FOLDER_ID
import by.coolightman.notes.util.ARG_FOLDER_ID
import by.coolightman.notes.util.NEW_FOLDER_COLOR_KEY
import by.coolightman.notes.util.toFormattedFullDate
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
class EditFolderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getFolderUseCase: GetFolderUseCase,
    private val updateFolderUseCase: UpdateFolderUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
    private val getIntPreferenceUseCase: GetIntPreferenceUseCase,
    private val putFolderInTrashUseCase: PutFolderInTrashUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditFolderUiState())
    val uiState: StateFlow<EditFolderUiState> = _uiState.asStateFlow()

    private var folder: Folder? = null
    private var extFolderId: Long = 0L

    init {
        val folderId = savedStateHandle.get<Long>(ARG_FOLDER_ID) ?: 0L
        extFolderId = savedStateHandle.get<Long>(ARG_EXTERNAL_FOLDER_ID) ?: 0L

        if (folderId != 0L) {
            getFolder(folderId)
        } else {
            getNewFolderColorPreference()
        }
    }

    private fun getNewFolderColorPreference() {
        viewModelScope.launch {
            getIntPreferenceUseCase(NEW_FOLDER_COLOR_KEY, ItemColor.GRAY.ordinal).collectLatest {
                _uiState.update { currentState ->
                    currentState.copy(colorIndex = it)
                }
            }
        }
    }

    private fun getFolder(folderId: Long) {
        viewModelScope.launch {
            folder = getFolderUseCase(folderId).first()
            folder?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        title = it.title,
                        createdAt = it.createdAt.toFormattedFullDate(),
                        colorIndex = it.colorIndex,
                        isPinned = it.isPinned
                    )
                }
            }
        }
    }

    fun saveFolder(
        title: String,
        colorIndex: Int,
        isPinned: Boolean
    ) {
        viewModelScope.launch {
            folder?.let {
                val updatedFolder = it.copy(
                    title = title,
                    colorIndex = colorIndex,
                    isPinned = isPinned
                )
                updateFolderUseCase(updatedFolder)
                return@launch
            }

            val createdFolder = Folder(
                title = title,
                colorIndex = colorIndex,
                createdAt = System.currentTimeMillis(),
                isInTrash = false,
                isSelected = false,
                isPinned = isPinned,
                externalFolderId = extFolderId
            )
            createFolderUseCase(createdFolder)
        }
    }

    fun sentFolderToTrash() {
        viewModelScope.launch {
            folder?.let {
                putFolderInTrashUseCase(it.id)
            }
        }
    }
}
