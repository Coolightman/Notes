package by.coolightman.notes.data.repository

import by.coolightman.notes.data.local.dao.TaskDao
import by.coolightman.notes.data.mappers.toTask
import by.coolightman.notes.data.mappers.toTaskDb
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun insert(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.insert(task.toTaskDb())
        }
    }

    override suspend fun getTask(taskId: Long): Task =
        withContext(Dispatchers.IO) {
            taskDao.getTask(taskId).toTask()
        }

    override fun getAll(): Flow<List<Task>> =
        taskDao.getAll()
            .map { list -> list.map { it.toTask() } }
            .flowOn(Dispatchers.IO)

    override suspend fun update(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.update(task.toTaskDb())
        }
    }

    override suspend fun delete(taskId: Long) {
        withContext(Dispatchers.IO) {
            taskDao.delete(taskId)
        }
    }

    override suspend fun deleteAllInactive() {
        withContext(Dispatchers.IO) {
            taskDao.deleteAllInactive()
        }
    }
}