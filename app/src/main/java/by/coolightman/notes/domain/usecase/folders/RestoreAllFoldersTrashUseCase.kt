package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RestoreAllFoldersTrashUseCase @Inject constructor(
    private val repository: FolderRepository,
    private val restoreFolderUseCase: RestoreFolderUseCase
) {
    suspend operator fun invoke() {
        val trashList = repository.getAllTrash().first()
        trashList.forEach { restoreFolderUseCase(it.id) }
    }
}
