package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PutSelectedNotesInTrashUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke() {
        val list = repository.getAllActive().first()
        val editedList = list.filter { it.isSelected }.map {
            it.copy(
                isInTrash = true,
                isSelected = false
            )
        }
        repository.updateList(editedList)
    }
}
