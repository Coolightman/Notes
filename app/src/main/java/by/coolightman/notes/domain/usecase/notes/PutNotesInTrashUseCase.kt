package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class PutNotesInTrashUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(list: List<Note>) {
        val editedList = list.map {
            it.copy(isInTrash = true)
        }
        repository.updateList(editedList)
    }
}
