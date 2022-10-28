package by.coolightman.notes.data.local.dao

import androidx.room.*
import by.coolightman.notes.data.local.dbModel.TaskDb
import by.coolightman.notes.data.local.dbModel.relations.TaskWithNotificationsDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskDb): Long

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTask(taskId: Long): TaskWithNotificationsDb

    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<TaskWithNotificationsDb>>

    @Query(
        "SELECT * FROM tasks JOIN tasksFts ON tasks.id == tasksFts.rowid " +
                "WHERE tasksFts MATCH :keyword ORDER by created_at DESC"
    )
    fun searchTask(keyword: String): Flow<List<TaskWithNotificationsDb>>

    @Update
    suspend fun update(task: TaskDb)

    @Update
    suspend fun updateList(list: List<TaskDb>)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun delete(taskId: Long)
}
