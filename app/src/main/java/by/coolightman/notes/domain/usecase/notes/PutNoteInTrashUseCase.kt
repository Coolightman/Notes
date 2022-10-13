package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class PutNoteInTrashUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(noteId: Long){
        val note = repository.getNote(noteId)
        val editedNote = note.copy(
            isInTrash = true
        )
        repository.update(editedNote)
    }
}