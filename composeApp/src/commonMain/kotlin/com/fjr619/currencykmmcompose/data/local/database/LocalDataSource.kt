package com.fjr619.currencykmmcompose.data.local.database

import com.fjr619.currencykmmcompose.data.local.database.model.CurrencyEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertCurrencyData(currency: CurrencyEntity)

    suspend fun insertCurrencyDatas(currenyList: List<CurrencyEntity>)
    fun readCurrencyData(): Flow<List<CurrencyEntity>>
    suspend fun cleanUp()
}