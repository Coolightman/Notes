package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.domain.repository.FolderRepository
import javax.inject.Inject

class PutFoldersInTrashUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(list: List<Folder>) {
        val editedList = list.map {
            it.copy(isInTrash = true)
        }
        repository.updateList(editedList)
    }
}
