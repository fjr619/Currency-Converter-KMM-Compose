package com.fjr619.currencykmmcompose.data.local.database

import com.fjr619.currencykmmcompose.data.local.database.model.Currency
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertCurrencyData(currency: Currency)
    fun readCurrencyData(): Flow<List<Currency>>
    suspend fun cleanUp()
}