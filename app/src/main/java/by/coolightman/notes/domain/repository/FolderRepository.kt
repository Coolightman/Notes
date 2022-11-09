package by.coolightman.notes.domain.repository

import by.coolightman.notes.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {

    suspend fun insert(folder: Folder)

    suspend fun get(folderId: Long): Folder

    fun getAll(): Flow<List<Folder>>

    suspend fun update(folder: Folder)

    suspend fun updateList(list: List<Folder>)

    suspend fun delete(folderId: Long)
}