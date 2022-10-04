package by.coolightman.notes.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun putInt(key: String, value: Int)

    fun getInt(key: String): Flow<Int>

    suspend fun putString(key: String, value: String)

    fun getString(key: String): Flow<String>
}