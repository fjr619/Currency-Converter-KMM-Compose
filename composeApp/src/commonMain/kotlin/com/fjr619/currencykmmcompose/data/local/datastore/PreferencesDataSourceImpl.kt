package com.fjr619.currencykmmcompose.data.local.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataSourceImpl(
    private val dataStoreProvider: DataStoreProvider,
) : PreferencesDataSource {

    private val keyTitle = stringPreferencesKey("testTitle")

    override fun getTitle(): Flow<String> =
        dataStoreProvider.dataStore.data.map {
            preferences -> preferences[keyTitle].orEmpty()
        }

    override suspend fun saveTitle(title: String) {
        dataStoreProvider.dataStore.edit {
            preferences -> preferences[keyTitle] = title
        }
    }

}