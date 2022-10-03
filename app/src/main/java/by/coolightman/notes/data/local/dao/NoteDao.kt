package by.coolightman.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import by.coolightman.notes.data.local.dbModel.NoteDb
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(note: NoteDb)

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNote(noteId: Long): NoteDb

    @Query("SELECT * FROM notes WHERE is_in_trash = 0 order by created_at desc")
    fun getAllActive(): Flow<List<NoteDb>>

    @Query("SELECT * FROM notes WHERE is_in_trash = 1")
    fun getTrash(): Flow<List<NoteDb>>

    @Query("SELECT COUNT(id) FROM notes WHERE is_in_trash = 1")
    fun getTrashCount(): Flow<Int>

    @Update
    suspend fun update(note: NoteDb)

    @Update
    suspend fun updateList(list: List<NoteDb>)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun delete(noteId: Long)

    @Query("DELETE FROM notes WHERE is_in_trash = 1")
    suspend fun deleteAllTrash()
}