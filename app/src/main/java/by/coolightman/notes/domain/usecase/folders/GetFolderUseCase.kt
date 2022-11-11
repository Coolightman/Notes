package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import javax.inject.Inject

class GetFolderUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(folderId: Long) = repository.get(folderId)
}
