package by.coolightman.notes.data.repositoryImpl

import by.coolightman.notes.data.database.dao.NoteDao
import by.coolightman.notes.data.mapper.toNote
import by.coolightman.notes.data.mapper.toNoteDb
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.insert(note.toNoteDb())
        }
    }

    override suspend fun getNote(noteId: Long) =
        withContext(Dispatchers.IO) {
            noteDao.getNote(noteId).toNote()
        }

    override fun getAllActive(): Flow<List<Note>> =
        noteDao.getAllActive()
            .map { list -> list.map { it.toNote() } }
            .flowOn(Dispatchers.IO)

    override fun getTrash(): Flow<List<Note>> =
        noteDao.getTrash()
            .map { list -> list.map { it.toNote() } }
            .flowOn(Dispatchers.IO)

    override fun getTrashCount(): Flow<Int> =
        noteDao.getTrashCount().flowOn(Dispatchers.IO)

    override suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.update(note.toNoteDb())
        }
    }

    override suspend fun updateList(list: List<Note>) {
        withContext(Dispatchers.IO) {
            noteDao.updateList(list.map { it.toNoteDb() })
        }
    }

    override suspend fun delete(noteId: Long) {
        withContext(Dispatchers.IO) {
            noteDao.delete(noteId)
        }
    }

    override suspend fun deleteAllTrash() {
        withContext(Dispatchers.IO) {
            noteDao.deleteAllTrash()
        }
    }
}