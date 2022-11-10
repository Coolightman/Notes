package by.coolightman.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import by.coolightman.notes.data.local.dbModel.FolderDb
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(folder: FolderDb)

    @Query("SELECT * FROM folders WHERE id = :folderId")
    suspend fun getFolder(folderId: Long): FolderDb

    @Query("SELECT * FROM folders WHERE id = :folderId")
    suspend fun getFolderMayNull(folderId: Long): FolderDb?

    @Query("SELECT * FROM folders where is_in_trash = 0")
    fun getAllActive(): Flow<List<FolderDb>>

    @Query("SELECT * FROM folders where is_in_trash = 1")
    fun getAllTrash(): Flow<List<FolderDb>>

    @Query("SELECT * FROM folders")
    suspend fun getAll(): List<FolderDb>

    @Query("SELECT COUNT(id) FROM folders WHERE is_in_trash = 1")
    fun getTrashCount(): Flow<Int>

    @Update
    suspend fun update(folder: FolderDb)

    @Update
    suspend fun updateList(list: List<FolderDb>)

    @Query("DELETE FROM folders WHERE id = :folderId")
    suspend fun delete(folderId: Long)
}
