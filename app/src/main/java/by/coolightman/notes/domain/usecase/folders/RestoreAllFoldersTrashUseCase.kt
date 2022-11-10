package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RestoreAllFoldersTrashUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke() {
        val trashList = repository.getAllTrash().first()
        val restoredList = trashList.map {
            it.copy(isInTrash = false)
        }
        repository.updateList(restoredList)
    }
}
