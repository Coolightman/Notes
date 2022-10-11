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
        return notesFlow.map { notesList ->
            val filteredList = mutableListOf<Note>()
            filteredList.apply {
                if (filterBy.contains(true)) {
                    filterBy.forEachIndexed { index, filterState ->
                        if (filterState) {
                            addAll(notesList.filter { note -> note.colorIndex == index })
                        }
                    }
                } else {
                    addAll(notesList)
                }
            }
        }
    }

    private fun sortList(notesFlow: Flow<List<Note>>, sortBy: SortBy): Flow<List<Note>> {
        return notesFlow.map { notesList ->
            when (sortBy) {
                SortBy.COLOR -> {
                    notesList.sortedBy { it.colorIndex }
                }
                SortBy.COLOR_DESC -> {
                    notesList.sortedByDescending { it.colorIndex }
                }
                SortBy.EDIT_DATE -> {
                    notesList.sortedBy {
                        if (it.isEdited) {
                            it.editedAt
                        } else {
                            it.createdAt
                        }
                    }
                }
                SortBy.EDIT_DATE_DESC -> {
                    notesList.sortedByDescending {
                        if (it.isEdited) {
                            it.editedAt
                        } else {
                            it.createdAt
                        }
                    }
                }
                SortBy.CREATE_DATE -> {
                    notesList.sortedBy { it.createdAt }
                }
                SortBy.CREATE_DATE_DESC -> {
                    notesList.sortedByDescending { it.createdAt }
                }
            }
        }
    }
}