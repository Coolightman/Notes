package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RestoreAllNotesTrashUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke() {
        val trashList = repository.getTrash().first()
        val restoredList = trashList.map {
            it.copy(
                isInTrash = false
            )
        }
        repository.updateList(restoredList)
    }
}
