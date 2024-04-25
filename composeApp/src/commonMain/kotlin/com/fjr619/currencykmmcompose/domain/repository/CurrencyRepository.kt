package com.fjr619.currencykmmcompose.domain.repository

import com.fjr619.currencykmmcompose.data.remote.model.response.CurrencyDto
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
//    suspend fun getLatestExchangeRates(): Result<List<Currency>>
    suspend fun isDataFresh(): Boolean
//    suspend fun saveSourceCurrencyCode(code: String)
//    suspend fun saveTargetCurrencyCode(code: String)
//    fun readSourceCurrencyCode(): Flow<CurrencyCode>
//    fun readTargetCurrencyCode(): Flow<CurrencyCode>
//    fun readCurrencyData(): Flow<Result<List<Currency>>>
//    suspend fun cleanUp()

    suspend fun fetchNewRates( onSucceed:(List<Currency>) -> Unit,
                               onFailled:() -> Unit)
}