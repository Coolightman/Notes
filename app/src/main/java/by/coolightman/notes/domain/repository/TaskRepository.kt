package by.coolightman.notes.domain.repository

import by.coolightman.notes.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insert(task: Task)

    suspend fun getTask(taskId: Long): Task

    fun getAllActive(): Flow<List<Task>>

    suspend fun update(task: Task)

    suspend fun delete(taskId: Long)

    suspend fun deleteAllInactive()
}