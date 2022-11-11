package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RestoreFolderUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(folderId: Long) {
        val folder = repository.get(folderId).first()
        if (folder.externalFolderId == 0L || isExternalFolderActive(folder.externalFolderId)) {
            val restoredFolder = folder.copy(isInTrash = false)
            repository.update(restoredFolder)
        } else {
            val restoredFolder = folder.copy(
                isInTrash = false,
                externalFolderId = 0
            )
            repository.update(restoredFolder)
        }
    }

    private suspend fun isExternalFolderActive(folderId: Long): Boolean {
        repository.getMayNull(folderId)?.let {
            return !it.isInTrash
        } ?: return false
    }
}
