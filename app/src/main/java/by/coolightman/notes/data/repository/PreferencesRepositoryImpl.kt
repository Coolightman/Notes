package by.coolightman.notes.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import by.coolightman.notes.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    override suspend fun putInt(key: String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override fun getInt(key: String): Flow<Int> {
        val dataStoreKey = intPreferencesKey(key)
        return dataStore.data
            .map { preferences -> preferences[dataStoreKey] ?: 0 }
    }

    override suspend fun putString(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override fun getString(key: String): Flow<String> {
        val dataStoreKey = stringPreferencesKey(key)
        return dataStore.data
            .map { preferences -> preferences[dataStoreKey] ?: "" }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {
        val dataStoreKey = booleanPreferencesKey(key)
        return dataStore.data
            .map { preferences -> preferences[dataStoreKey] ?: defaultValue }
    }
}
