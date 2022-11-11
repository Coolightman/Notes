package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DeleteAllFoldersTrashUseCase @Inject constructor(
    private val repository: FolderRepository,
    private val deleteFolderUseCase: DeleteFolderUseCase
) {
    suspend operator fun invoke() {
        repository.getAllTrash().first().forEach {
            deleteFolderUseCase(it.id)
        }
    }
}
