package by.coolightman.notes.data.repository

import by.coolightman.notes.data.local.dao.FolderDao
import by.coolightman.notes.data.mappers.toFolder
import by.coolightman.notes.data.mappers.toFolderDb
import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FolderRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao
) : FolderRepository {

    override suspend fun insert(folder: Folder) {
        folderDao.insert(folder.toFolderDb())
    }

    override suspend fun get(folderId: Long): Folder =
        folderDao.getFolder(folderId).toFolder()

    override fun getAll(): Flow<List<Folder>> =
        folderDao.getAll().map { list -> list.map { it.toFolder() } }

    override suspend fun update(folder: Folder) {
        folderDao.update(folder.toFolderDb())
    }

    override suspend fun updateList(list: List<Folder>) {
        folderDao.updateList(list.map { it.toFolderDb() })
    }

    override suspend fun delete(folderId: Long) {
        folderDao.delete(folderId)
    }
}