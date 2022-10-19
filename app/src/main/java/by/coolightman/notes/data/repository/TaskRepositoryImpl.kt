package by.coolightman.notes.data.repository

import by.coolightman.notes.data.local.dao.TaskDao
import by.coolightman.notes.data.local.dbModel.TaskDb
import by.coolightman.notes.data.mappers.toTask
import by.coolightman.notes.data.mappers.toTaskDb
import by.coolightman.notes.domain.model.Task
import by.coolightman.notes.domain.repository.TaskRepository
import by.coolightman.notes.util.isOld
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun insert(task: Task): Long {
        return taskDao.insert(task.toTaskDb())
    }

    override suspend fun getTask(taskId: Long): Task =
        taskDao.getTask(taskId).toTask()

    override fun getAll(): Flow<List<Task>> =
        taskDao.getAll()
            .map { list ->
                checkExpiredNotifications(list)
                list.map { it.toTask() }
            }

    private suspend fun checkExpiredNotifications(list: List<TaskDb>) {
        val expiredList = list
            .filter { it.isHasNotification && it.notificationTime.isOld() }
            .map {
                it.copy(isHasNotification = false)
            }
        taskDao.updateList(expiredList)
    }

    override fun searchTask(key: String): Flow<List<Task>> =
        taskDao.searchTask("$key*").map { list -> list.map { it.toTask() } }

    override suspend fun update(task: Task) {
        taskDao.update(task.toTaskDb())
    }

    override suspend fun updateList(list: List<Task>) {
        taskDao.updateList(list.map { it.toTaskDb() })
    }

    override suspend fun delete(taskId: Long) {
        taskDao.delete(taskId)
    }

    override suspend fun deleteAllInactive() {
        taskDao.deleteAllInactive()
    }
}
