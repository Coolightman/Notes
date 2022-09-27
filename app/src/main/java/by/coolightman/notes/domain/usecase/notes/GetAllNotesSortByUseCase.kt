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
            is SortNoteBy.Color -> {
                repository.getAllActive().map { list -> list.sortedBy { it.color } }
            }
            is SortNoteBy.ColorDesc -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.color } }
            }
            is SortNoteBy.EditDate -> {
                repository.getAllActive().map { list -> list.sortedBy { it.editedAt } }
            }
            is SortNoteBy.EditDateDesc -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.editedAt } }
            }
            is SortNoteBy.CreateDate -> {
                repository.getAllActive().map { list -> list.sortedBy { it.createdAt } }
            }
            is SortNoteBy.CreateDateDesc -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.createdAt } }
            }
            is SortNoteBy.Title -> {
                repository.getAllActive().map { list -> list.sortedBy { it.title } }
            }
            is SortNoteBy.TitleDesc -> {
                repository.getAllActive().map { list -> list.sortedByDescending { it.title } }
            }
        }
    }
}