package com.fjr619.currencykmmcompose.data.repository

import com.fjr619.currencykmmcompose.data.local.database.LocalDataSource
import com.fjr619.currencykmmcompose.data.local.database.model.toDomain
import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSource
import com.fjr619.currencykmmcompose.data.remote.RemoteDataSource
import com.fjr619.currencykmmcompose.data.remote.model.response.CurrencyDto
import com.fjr619.currencykmmcompose.data.remote.model.response.RequestException
import com.fjr619.currencykmmcompose.data.remote.model.response.toDomain
import com.fjr619.currencykmmcompose.data.remote.model.response.toEntity
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.repository.CurrencyRepository
import com.fjr619.currencykmmcompose.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class CurrencyRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val preferencesDataSource: PreferencesDataSource,
    private val localDataSource: LocalDataSource
) : CurrencyRepository {
    private fun readLocalCurrencyDatas(): Flow<Result<List<Currency>>> {
        return localDataSource.readCurrencyData().map { list ->
            Result.success(list.map { it.toDomain() })
        }
    }

    override suspend fun isDataFresh(): Boolean {
        val savedTimestamp = preferencesDataSource.getPreference(
            key = Constant.KEY_TIMESTAMP,
            defaultValue = 0L
        ).first()

        return if (savedTimestamp != 0L) {
            val currentInstant = Clock.System.now()
            val savedInstant = Instant.fromEpochMilliseconds(savedTimestamp)

            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())

            val difference = currentDateTime.time.toSecondOfDay() - savedDateTime.time.toSecondOfDay()

            println("currentDateTime $currentDateTime")
            println("savedDateTime $savedDateTime")
            println("difference $difference")

            val daysDifference = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear
//
            daysDifference < 1
        } else false
    }

    private suspend fun getLatestExchangeRatesFromNetwork(): Result<List<Currency>> {
        try {
            //fetch from network
            val response = remoteDataSource.getLatest()

            val avaiableCurrencyCodes = response.data.keys
                .filter {
                    CurrencyCode.entries.map { code ->
                        code.name
                    }.toSet().contains(it)
                }

            val avaiableCurrencies = response.data.values
                .filter { currency -> avaiableCurrencyCodes.contains(currency.code) }


            //update database
            if (avaiableCurrencies.isNotEmpty()) {
                println("FETCHING NETWORK AND SAVE DATABASE")
                localDataSource.cleanUp()
                localDataSource.insertCurrencyDatas( avaiableCurrencies.map {
                    it.toEntity()
                })
            }

            //persist a timestamp
//            val lastUpdate = response.meta.lastUpdatedAt
            preferencesDataSource.putPreference(Constant.KEY_TIMESTAMP, Clock.System.now().toEpochMilliseconds())
            return Result.success(value = avaiableCurrencies.map { it.toDomain() })

        } catch (e: RequestException) {
            return Result.failure(e)
        }
    }

    private suspend fun cacheTheData(
        onSucceed:(List<Currency>) -> Unit,
        onFailled:() -> Unit
    ) {
        val fetchedData = getLatestExchangeRatesFromNetwork()
        if (fetchedData.isSuccess) {
            val data = fetchedData.getOrNull()
            data?.let { nonNullData ->
                println("FETCHING NETWORK DONE, HAS DATA")
                onSucceed(nonNullData)
            }?: run {
                println("FETCHING NETWORK DONE, HAS NULL DATA")
                onFailled()
            }
        } else {
            println("FETCHING NETWORK ERROR ${fetchedData.exceptionOrNull()?.message}")
            onFailled()
        }


    }

    override suspend fun fetchNewRates(
        onSucceed:(List<Currency>) -> Unit,
        onFailled:() -> Unit
    ) {
        val localCache = readLocalCurrencyDatas().first()
        if (localCache.isSuccess) {
            val data = localCache.getOrNull()
            data?.let { nonNullData ->
                if (nonNullData.isNotEmpty()) {
                    if (!isDataFresh()) {
                        println("DATA NOT FRESH")
                        cacheTheData(onSucceed, onFailled)
                    } else {
                        println("DATA IS FRESH")
                        onSucceed(nonNullData)
                    }

                } else {
                    println("DATABASE NEEDS DATA")
                    cacheTheData(onSucceed, onFailled)
                }
            } ?: run {
                println("DATABASE NEEDS DATA")
                cacheTheData(onSucceed, onFailled)
            }
        } else {
            println("ERROR READING LOCAL DATABASE")
            onFailled()
        }
    }

//    override suspend fun getLatestExchangeRates(): Result<List<Currency>> {
//        try {
//            val response = remoteDataSource.getLatest()
//
//            val avaiableCurrencyCodes = response.data.keys
//                .filter {
//                    CurrencyCode.entries.map { code ->
//                        code.name
//                    }.toSet().contains(it)
//                }
//
//            val avaiableCurrencies = response.data.values
//                .filter { currency -> avaiableCurrencyCodes.contains(currency.code) }
//                .map {
//                    it.toDomain()
//                }
//
//            //persist a timestamp
//            val lastUpdate = response.meta.lastUpdatedAt
//            preferencesDataSource.putPreference(Constant.KEY_TIMESTAMP, Instant.parse(lastUpdate).toEpochMilliseconds())
//            return Result.success(value = avaiableCurrencies)
//
//        } catch (e: RequestException) {
//            return Result.failure(e)
//        }
//    }
//
//    override suspend fun isDataFresh(): Boolean {
//        val savedTimestamp = preferencesDataSource.getPreference(
//            key = Constant.KEY_TIMESTAMP,
//            defaultValue = 0L
//        ).first()
//
//        return if (savedTimestamp != 0L) {
//            val currentInstant = Clock.System.now()
//            val savedInstant = Instant.fromEpochMilliseconds(savedTimestamp)
//
//            val currentDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
//            val savedDateTime = savedInstant.toLocalDateTime(TimeZone.currentSystemDefault())
//
//            val daysDifference = currentDateTime.date.dayOfYear - savedDateTime.date.dayOfYear
//
//            daysDifference < 1
//        } else false
//    }
//
//    override suspend fun saveSourceCurrencyCode(code: String) {
//        preferencesDataSource.putPreference(Constant.KEY_SOURCE_CURRENCY, code)
//    }
//
//    override suspend fun saveTargetCurrencyCode(code: String) {
//        preferencesDataSource.putPreference(Constant.KEY_TARGET_CURRENCY, code)
//    }
//
//    override fun readSourceCurrencyCode(): Flow<CurrencyCode> {
//        return preferencesDataSource
//            .getPreference(Constant.KEY_SOURCE_CURRENCY, Constant.DEFAULT_SOURCE_CURRENCY)
//            .map {
//                CurrencyCode.valueOf(it)
//            }
//    }
//
//    override fun readTargetCurrencyCode(): Flow<CurrencyCode> {
//        return preferencesDataSource
//            .getPreference(Constant.KEY_TARGET_CURRENCY, Constant.DEFAULT_TARGET_CURRENCY)
//            .map {
//                CurrencyCode.valueOf(it)
//            }
//    }
//
//    override fun readCurrencyData(): Flow<Result<List<Currency>>> {
//        return localDataSource.readCurrencyData().map { list ->
//            Result.success(list.map { it.toDomain() })
//        }
//    }
//
//    override suspend fun cleanUp() {
//        localDataSource.cleanUp()
//    }


}