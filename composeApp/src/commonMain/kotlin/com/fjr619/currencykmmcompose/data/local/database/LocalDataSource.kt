package com.fjr619.currencykmmcompose.data.local.database

import com.fjr619.currencykmmcompose.data.local.database.model.CurrencyDao
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertCurrencyData(currency: CurrencyDao)
    fun readCurrencyData(): Flow<List<CurrencyDao>>
    suspend fun cleanUp()
}