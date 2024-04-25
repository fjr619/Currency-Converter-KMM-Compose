package com.fjr619.currencykmmcompose.utils

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {
     val keyTitle = stringPreferencesKey("testTitle")
     val KEY_TIMESTAMP = longPreferencesKey("lastUpdated")
}