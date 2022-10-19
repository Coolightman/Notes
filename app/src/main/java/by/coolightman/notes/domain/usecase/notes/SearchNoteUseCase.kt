package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class SearchNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(key: String) = repository.searchNote(key)
}
