package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.FolderRepository
import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class RestoreNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(noteId: Long) {
        val note = repository.getNote(noteId)
        if (note.folderId == 0L || isFolderActive(note.folderId)) {
            val restoredNote = note.copy(isInTrash = false)
            repository.update(restoredNote)
        } else {
            val restoredNote = note.copy(
                isInTrash = false,
                folderId = 0
            )
            repository.update(restoredNote)
        }
    }

    private suspend fun isFolderActive(folderId: Long): Boolean {
        folderRepository.getMayNull(folderId)?.let {
            return !it.isInTrash
        } ?: return false
    }
}
