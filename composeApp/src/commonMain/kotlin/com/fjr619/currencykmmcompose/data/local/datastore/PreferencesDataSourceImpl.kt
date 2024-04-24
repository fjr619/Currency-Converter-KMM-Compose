package com.fjr619.currencykmmcompose.data.local.datastore

import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PreferencesDataSourceImpl(
    private val dataStoreProvider: DataStoreProvider,
) : PreferencesDataSource {
    override fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataStoreProvider.dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
                preferences -> preferences[key] ?: defaultValue
        }

    override suspend fun <T> putPreference(key: Preferences.Key<T>, value: T) {
        dataStoreProvider.dataStore.edit {
            preferences -> preferences[key] = value
        }
    }

    override suspend fun <T> removePreference(key: Preferences.Key<T>) {
        dataStoreProvider.dataStore.edit {
            preferences -> preferences.remove(key)
        }
    }

    override suspend fun clearAllPreference() {
        dataStoreProvider.dataStore.edit {
            preferences -> preferences.clear()
        }
    }

}