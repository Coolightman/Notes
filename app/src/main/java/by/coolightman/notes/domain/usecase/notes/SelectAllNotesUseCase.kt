package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SelectAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke() {
        val list = repository.getAllActive().first()
        val editedList = list.map { it.copy(isSelected = true) }
        repository.updateList(editedList)
    }
}