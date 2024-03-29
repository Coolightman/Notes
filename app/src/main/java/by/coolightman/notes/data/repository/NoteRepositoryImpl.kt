package by.coolightman.notes.data.repository

import by.coolightman.notes.data.local.dao.NoteDao
import by.coolightman.notes.data.mappers.toNote
import by.coolightman.notes.data.mappers.toNoteDb
import by.coolightman.notes.domain.model.Note
import by.coolightman.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun create(note: Note) {
        noteDao.insert(note.toNoteDb())
    }

    override suspend fun getNote(noteId: Long) =
        noteDao.getNote(noteId).toNote()

    override fun getAllMainActive(): Flow<List<Note>> =
        noteDao.getAllMainActive()
            .map { list -> list.map { it.toNote() } }

    override fun getAllActiveByFolder(folderId: Long): Flow<List<Note>> =
        noteDao.getAllActiveByFolder(folderId)
            .map { list -> list.map { it.toNote() } }

    override suspend fun getAll(): List<Note> =
        noteDao.getAll().map { it.toNote() }

    override fun getTrash(): Flow<List<Note>> =
        noteDao.getTrash()
            .map { list -> list.map { it.toNote() } }

    override fun getTrashCount(): Flow<Int> =
        noteDao.getTrashCount().flowOn(Dispatchers.IO)

    override fun searchNote(key: String): Flow<List<Note>> =
        noteDao.searchNote("$key*").map { list -> list.map { it.toNote() } }

    override suspend fun update(note: Note) {
        noteDao.update(note.toNoteDb())
    }

    override suspend fun updateList(list: List<Note>) {
        noteDao.updateList(list.map { it.toNoteDb() })
    }

    override suspend fun delete(noteId: Long) {
        noteDao.delete(noteId)
    }

    override suspend fun deleteAllTrash() {
        noteDao.deleteAllTrash()
    }
}
