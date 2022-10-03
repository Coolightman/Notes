package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.model.SortNotesBy
import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNotesSortByUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(sortBy: SortNotesBy): Flow<List<Note>> {
        return when (sortBy) {
            SortNotesBy.COLOR -> {
                repository.getAllActive().map { list -> list.sortedBy { it.colorIndex } }
            }
            SortNotesBy.COLOR_DESC -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.colorIndex } }
            }
            SortNotesBy.EDIT_DATE -> {
                repository.getAllActive().map { list -> list.sortedBy { it.editedAt } }
            }
            SortNotesBy.EDIT_DATE_DESC -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.editedAt } }
            }
            SortNotesBy.CREATE_DATE -> {
                repository.getAllActive().map { list -> list.sortedBy { it.createdAt } }
            }
            SortNotesBy.CREATE_DATE_DESC -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.createdAt } }
            }
            SortNotesBy.TITLE -> {
                repository.getAllActive().map { list -> list.sortedBy { it.title } }
            }
            SortNotesBy.TITLE_DESC -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.title } }
            }
        }
    }
}