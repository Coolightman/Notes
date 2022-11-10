package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllMainFoldersUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    operator fun invoke() =
        folderRepository.getAllMainActive()
            .map { it.sortedByDescending { folder -> folder.isPinned } }
}