package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class SetIsSelectedNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(noteId: Long){
        val note = noteRepository.getNote(noteId)
        val editedNote = note.copy(
            isSelected = !note.isSelected
        )
        noteRepository.update(editedNote)
    }
}