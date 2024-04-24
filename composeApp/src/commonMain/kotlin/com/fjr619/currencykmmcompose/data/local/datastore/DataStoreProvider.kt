package com.fjr619.currencykmmcompose.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

interface DataStoreProvider {

    val dataStore: DataStore<Preferences>
}