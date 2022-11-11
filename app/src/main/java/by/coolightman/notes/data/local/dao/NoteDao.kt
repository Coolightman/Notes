package by.coolightman.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
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

    @Query("SELECT * FROM notes WHERE is_in_trash = 0 AND folder_id = 0")
    fun getAllMainActive(): Flow<List<NoteDb>>

    @Query("SELECT * FROM notes WHERE is_in_trash = 0 AND folder_id = :folderId")
    fun getAllActiveByFolder(folderId: Long): Flow<List<NoteDb>>

    @Query("SELECT * FROM notes")
    suspend fun getAll(): List<NoteDb>

    @Query("SELECT * FROM notes WHERE is_in_trash = 1")
    fun getTrash(): Flow<List<NoteDb>>

    @Query("SELECT COUNT(id) FROM notes WHERE is_in_trash = 1")
    fun getTrashCount(): Flow<Int>

    @Query(
        "SELECT * FROM notes JOIN notesFts ON notes.id == notesFts.rowid " +
                "WHERE notesFts MATCH :keyword AND is_in_trash = 0 ORDER BY created_at DESC"
    )
    fun searchNote(keyword: String): Flow<List<NoteDb>>

    @Update
    suspend fun update(note: NoteDb)

    @Update
    suspend fun updateList(list: List<NoteDb>)

    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun delete(noteId: Long)

    @Query("DELETE FROM notes WHERE is_in_trash = 1")
    suspend fun deleteAllTrash()
}
