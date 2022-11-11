package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.domain.repository.FolderRepository
import javax.inject.Inject

class GetInnerFoldersUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(folderId: Long): List<Folder> {
        return repository.getAll().filter { it.externalFolderId == folderId }
    }
}