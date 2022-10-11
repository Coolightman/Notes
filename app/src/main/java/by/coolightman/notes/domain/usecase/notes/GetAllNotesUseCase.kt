package by.coolightman.notes.domain.usecase.notes

import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.model.SortBy
import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(sortBy: SortBy, filterBy: List<Boolean>): Flow<List<Note>> {
        val notesFlow = repository.getAllActive()
        val filteredFlow = filterList(notesFlow, filterBy)
        return sortList(filteredFlow, sortBy)
    }

    private fun filterList(notesFlow: Flow<List<Note>>, filterBy: List<Boolean>): Flow<List<Note>> {
        return notesFlow.map {
            val filteredList = mutableListOf<Note>()
            if (filterBy.contains(true)) {
                filterBy.forEachIndexed { index, filterState ->
                    if (filterState) {
                        filteredList.addAll(it.filter { note -> note.colorIndex == index })
                    }
                }
            } else {
                filteredList.addAll(it)
            }
            filteredList
        }
    }


private fun sortList(notesFlow: Flow<List<Note>>, sortBy: SortBy): Flow<List<Note>> {
    return when (sortBy) {
        SortBy.COLOR -> {
            notesFlow.map { list -> list.sortedBy { it.colorIndex } }
        }
        SortBy.COLOR_DESC -> {
            notesFlow.map { list -> list.sortedByDescending { it.colorIndex } }
        }
        SortBy.EDIT_DATE -> {
            notesFlow.map { list ->
                list
                    .sortedBy {
                        if (it.isEdited) {
                            it.editedAt
                        } else {
                            it.createdAt
                        }
                    }
            }
        }
        SortBy.EDIT_DATE_DESC -> {
            notesFlow.map { list ->
                list
                    .sortedByDescending {
                        if (it.isEdited) {
                            it.editedAt
                        } else {
                            it.createdAt
                        }
                    }
            }
        }
        SortBy.CREATE_DATE -> {
            notesFlow.map { list -> list.sortedBy { it.createdAt } }
        }
        SortBy.CREATE_DATE_DESC -> {
            notesFlow.map { list -> list.sortedByDescending { it.createdAt } }
        }
    }
}
}