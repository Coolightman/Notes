package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import javax.inject.Inject

class RestoreFolderUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(folderId: Long) {
        val note = repository.get(folderId)
        val restoredNote = note.copy(isInTrash = false)
        repository.update(restoredNote)
    }
}
