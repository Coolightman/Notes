package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.model.SortNoteBy
import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNotesSortByUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(sortBy: SortNoteBy): Flow<List<Note>> {
        return when (sortBy) {
            SortNoteBy.COLOR -> {
                repository.getAllActive().map { list -> list.sortedBy { it.colorIndex } }
            }
            SortNoteBy.COLOR_DESC -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.colorIndex } }
            }
            SortNoteBy.EDIT_DATE -> {
                repository.getAllActive().map { list -> list.sortedBy { it.editedAt } }
            }
            SortNoteBy.EDIT_DATE_DESC -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.editedAt } }
            }
            SortNoteBy.CREATE_DATE -> {
                repository.getAllActive().map { list -> list.sortedBy { it.createdAt } }
            }
            SortNoteBy.CREATE_DATE_DESC -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.createdAt } }
            }
            SortNoteBy.TITLE -> {
                repository.getAllActive().map { list -> list.sortedBy { it.title } }
            }
            SortNoteBy.TITLE_DESC -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.title } }
            }
        }
    }
}