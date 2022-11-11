package by.coolightman.notes.domain.repository

import by.coolightman.notes.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {

    suspend fun insert(folder: Folder)

    fun get(folderId: Long): Flow<Folder>

    suspend fun getMayNull(folderId: Long): Folder?

    fun getAllMainActive(): Flow<List<Folder>>

    fun getAllActive(): Flow<List<Folder>>

    fun getAllActiveByExternalFolder(extFolderId: Long): Flow<List<Folder>>

    fun getAllTrash(): Flow<List<Folder>>

    suspend fun getAll(): List<Folder>

    suspend fun update(folder: Folder)

    suspend fun updateList(list: List<Folder>)

    suspend fun delete(folderId: Long)

    fun getTrashCount(): Flow<Int>
}