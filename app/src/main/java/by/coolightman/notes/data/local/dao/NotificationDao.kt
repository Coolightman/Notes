package by.coolightman.notes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import by.coolightman.notes.data.local.dbModel.NotificationDb

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationDb): Long

    @Query("SELECT * FROM notifications WHERE id = :id")
    suspend fun get(id: Int): NotificationDb

    @Query("SELECT * FROM notifications")
    suspend fun getAll(): List<NotificationDb>

    @Query("SELECT * FROM notifications WHERE task_id = :taskId")
    suspend fun getAllByTask(taskId: Long): List<NotificationDb>

    @Update
    suspend fun update(notification: NotificationDb)

    @Update
    suspend fun updateList(list: List<NotificationDb>)

    @Query("DELETE FROM notifications WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM notifications WHERE task_id = :taskId")
    suspend fun deleteByTask(taskId: Long)
}