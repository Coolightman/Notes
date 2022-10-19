package by.coolightman.notes.data.local.dao

import androidx.room.*
import by.coolightman.notes.data.local.dbModel.TaskDb
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskDb): Long

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTask(taskId: Long): TaskDb

    @Query("SELECT * FROM tasks")
    fun getAll(): Flow<List<TaskDb>>

    @Query(
        "SELECT * FROM tasks JOIN tasksFts ON tasks.id == tasksFts.rowid " +
                "WHERE tasksFts MATCH :keyword ORDER by created_at DESC"
    )
    fun searchTask(keyword: String): Flow<List<TaskDb>>

    @Update
    suspend fun update(task: TaskDb)

    @Update
    suspend fun updateList(list: List<TaskDb>)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun delete(taskId: Long)

    @Query("DELETE FROM tasks WHERE is_active = 0")
    suspend fun deleteAllInactive()
}
