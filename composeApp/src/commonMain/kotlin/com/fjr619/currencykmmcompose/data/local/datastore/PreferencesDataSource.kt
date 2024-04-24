package com.fjr619.currencykmmcompose.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    fun getTitle(): Flow<String>
    suspend fun saveTitle(title: String)
}