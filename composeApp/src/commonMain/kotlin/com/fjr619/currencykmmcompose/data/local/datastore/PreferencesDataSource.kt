package com.fjr619.currencykmmcompose.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    fun<T> getPreference(key: Preferences.Key<T>, defaultValue : T): Flow<T>
    suspend fun<T> putPreference(key: Preferences.Key<T>, value: T)
    suspend fun<T> removePreference(key: Preferences.Key<T>)
    suspend fun clearAllPreference()
}