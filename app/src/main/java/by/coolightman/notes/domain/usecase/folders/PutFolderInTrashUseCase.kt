package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import javax.inject.Inject

class PutFolderInTrashUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(folderId: Long) {
        val folder = repository.get(folderId)
        val editedFolder = folder.copy(
            isInTrash = true
        )
        repository.update(editedFolder)
    }
}
