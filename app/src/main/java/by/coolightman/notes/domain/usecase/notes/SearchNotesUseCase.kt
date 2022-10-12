package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class SearchNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(key: String) {

    }
}