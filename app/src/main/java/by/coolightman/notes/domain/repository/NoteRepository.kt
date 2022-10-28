package by.coolightman.notes.domain.repository

import by.coolightman.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun create(note: Note)

    suspend fun getNote(noteId: Long): Note

    fun getAllActive(): Flow<List<Note>>

    fun getTrash(): Flow<List<Note>>

    fun getTrashCount(): Flow<Int>

    fun searchNote(key: String): Flow<List<Note>>

    suspend fun update(note: Note)

    suspend fun updateList(list: List<Note>)

    suspend fun delete(noteId: Long)

    suspend fun deleteAllTrash()
}
