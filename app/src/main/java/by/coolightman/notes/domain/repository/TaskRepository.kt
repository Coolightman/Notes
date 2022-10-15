package by.coolightman.notes.domain.repository

import by.coolightman.notes.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insert(task: Task): Long

    suspend fun getTask(taskId: Long): Task

    fun getAll(): Flow<List<Task>>

    fun searchTask(key: String): Flow<List<Task>>

    suspend fun update(task: Task)

    suspend fun updateList(list: List<Task>)

    suspend fun delete(taskId: Long)

    suspend fun deleteAllInactive()
}