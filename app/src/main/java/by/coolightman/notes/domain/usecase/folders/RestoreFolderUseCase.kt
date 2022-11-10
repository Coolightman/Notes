package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import javax.inject.Inject

class RestoreFolderUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(folderId: Long) {
        val folder = repository.get(folderId)
        if (folder.externalFolderId == 0L || isFolderExist(folder.externalFolderId)) {
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

    private suspend fun isFolderExist(folderId: Long): Boolean {
        return repository.getMayNull(folderId) == null
    }
}
