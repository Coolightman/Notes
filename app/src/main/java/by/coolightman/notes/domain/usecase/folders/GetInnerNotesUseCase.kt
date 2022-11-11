package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class GetInnerNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(folderId: Long): List<Note> {
        return repository.getAll().filter { it.folderId == folderId }
    }
}