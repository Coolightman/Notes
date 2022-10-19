package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class RestoreNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Long) {
        val note = repository.getNote(noteId)
        val restoredNote = note.copy(
            isInTrash = false
        )
        repository.update(restoredNote)
    }
}
