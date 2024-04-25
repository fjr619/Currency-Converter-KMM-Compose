package com.fjr619.currencykmmcompose.utils

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode

object Constant {
     val keyTitle = stringPreferencesKey("testTitle")
     val KEY_TIMESTAMP = longPreferencesKey("lastUpdated")
     val KEY_SOURCE_CURRENCY = stringPreferencesKey("sourceCurrency")
     val KEY_TARGET_CURRENCY = stringPreferencesKey("targetCurrency")
     val DEFAULT_SOURCE_CURRENCY = CurrencyCode.USD.name
     val DEFAULT_TARGET_CURRENCY = CurrencyCode.EUR.name
}