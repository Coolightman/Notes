package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesTrashCountUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke() = repository.getTrashCount()
}
