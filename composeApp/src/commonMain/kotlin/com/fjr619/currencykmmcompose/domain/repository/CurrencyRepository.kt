package com.fjr619.currencykmmcompose.domain.repository

import com.fjr619.currencykmmcompose.data.remote.model.response.CurrencyDto
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getLatestExchangeRates(): Result<List<Currency>>
    suspend fun isDataFresh(): Boolean
}