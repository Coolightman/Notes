package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ResetNotesSelectionsUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke() {
        val list = noteRepository.getAllActive().first()
        val editedList = list.map {
            it.copy(
                isSelected = false
            )
        }
        noteRepository.updateList(editedList)
    }
}