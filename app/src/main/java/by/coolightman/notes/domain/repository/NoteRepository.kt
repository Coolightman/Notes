package by.coolightman.notes.domain.repository

import by.coolightman.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note)

    suspend fun getNote(noteId: Long): Note

    fun getAll(): Flow<List<Note>>

    fun getTrash(): Flow<List<Note>>

    fun getTrashCount(): Flow<Int>

    suspend fun update(note: Note)

    suspend fun putInTrash(noteId: Long)

    suspend fun restoreFromTrash(noteId: Long)

    suspend fun delete(noteId: Long)

    suspend fun deleteAllTrash()

    suspend fun restoreAllTrash()

    suspend fun exportNotes()

    suspend fun importNotes()
}