package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllFoldersByExternalFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    operator fun invoke(extFolderId: Long) =
        folderRepository.getAllActiveByExternalFolder(extFolderId)
            .map { list ->
                list
                    .sortedBy { it.title }
                    .sortedByDescending { it.isPinned }
            }
}