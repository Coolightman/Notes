package by.coolightman.notes.domain.repository

import by.coolightman.notes.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insert(task: Task)

    fun getAll(): Flow<List<Task>>

    suspend fun getTask(taskId: Long): Task

    suspend fun update(task: Task)

    suspend fun switchActivity(taskId: Long)

    suspend fun hide(taskId: Long)

    suspend fun delete(taskId: Long)

    suspend fun deleteAllInactive()

    suspend fun exportTasks()

    suspend fun importTasks()
}