package com.fjr619.currencykmmcompose.data.repository

import com.fjr619.currencykmmcompose.data.local.database.LocalDataSource
import com.fjr619.currencykmmcompose.data.local.database.model.toDomain
import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSource
import com.fjr619.currencykmmcompose.data.remote.RemoteDataSource
import com.fjr619.currencykmmcompose.data.remote.model.response.RequestException
import com.fjr619.currencykmmcompose.data.remote.model.response.toDomain
import com.fjr619.currencykmmcompose.data.remote.model.response.toEntity
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.repository.CurrencyRepository
import com.fjr619.currencykmmcompose.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeMark
import kotlin.time.TimeSource

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

    private fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period)
        }
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
                localDataSource.insertCurrencyDatas(avaiableCurrencies.map {
                    it.toEntity()
                })
            }

            //persist a timestamp
//            val lastUpdate = response.meta.lastUpdatedAt
            preferencesDataSource.putPreference(
                Constant.KEY_TIMESTAMP,
                Clock.System.now().toEpochMilliseconds()
            )
            return Result.success(value = avaiableCurrencies.map { it.toDomain() })
        } catch (e: RequestException) {
            return Result.failure(e)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private suspend fun cacheTheData(
        onSucceed: suspend (List<Currency>) -> Unit,
        onFailled: suspend (message: String) -> Unit
    ) {
        val fetchedData = getLatestExchangeRatesFromNetwork()
        if (fetchedData.isSuccess) {
            val data = fetchedData.getOrNull()
            data?.let { nonNullData ->
                println("FETCHING NETWORK DONE, HAS DATA")
                onSucceed(nonNullData)
            } ?: run {
                println("FETCHING NETWORK DONE, HAS NULL DATA")
                onFailled("FETCHING NETWORK DONE, HAS NULL DATA")
            }
        } else {
            println("FETCHING NETWORK ERROR ${fetchedData.exceptionOrNull()?.message}")
            onFailled("FETCHING NETWORK ERROR ${fetchedData.exceptionOrNull()?.message}")
        }


    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun isDataFresh(): Flow<Boolean> {
        return preferencesDataSource.getPreference(
            key = Constant.KEY_TIMESTAMP,
            defaultValue = 0L
        ).flatMapLatest {
            val savedTimestamp = it
            if (savedTimestamp != 0L) {

                var currentDateTime: LocalDateTime
                val savedInstant = Instant.fromEpochMilliseconds(savedTimestamp)

                val result = tickerFlow(1.seconds).map {
                    currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val difference = savedInstant.until(
                        currentDateTime.toInstant(TimeZone.currentSystemDefault()),
                        DateTimeUnit.DAY,
                        TimeZone.currentSystemDefault()
                    )

                    difference < 1
                }

                result

            } else flow { emit(false) }.flowOn(Dispatchers.IO)
        }
    }

    override suspend fun fetchNewRates(): Flow<Result<List<Currency>>> {
        return flow {
            val localCache = readLocalCurrencyDatas().first()
            if (localCache.isSuccess) {
                val data = localCache.getOrNull()
                data?.let { nonNullData ->
                    if (nonNullData.isNotEmpty()) {
                        if (!isDataFresh().first()) {
                            println("DATA NOT FRESH")
                            cacheTheData({
                                       emit(Result.success(it))
                            }, {
                                emit(Result.failure(Throwable(message = it)))
                            })
                        } else {
                            println("DATA IS FRESH")
                            emit(Result.success(nonNullData))
                        }

                    } else {
                        println("DATABASE NEEDS DATA")
                        cacheTheData({
                            emit(Result.success(it))
                        }, {
                            emit(Result.failure(Throwable(message = it)))
                        })
                    }
                } ?: run {
                    println("DATABASE NEEDS DATA")
                    cacheTheData({
                        emit(Result.success(it))
                    }, {
                        emit(Result.failure(Throwable(message = it)))
                    })
                }
            } else {
                println("ERROR READING LOCAL DATABASE")
                emit(Result.failure(Throwable(message = "ERROR READING LOCAL DATABASE")))
            }
        }

    }

    override suspend fun saveSourceCurrencyCode(code: String) {
        preferencesDataSource.putPreference(Constant.KEY_SOURCE_CURRENCY, code)
    }

    override suspend fun saveTargetCurrencyCode(code: String) {
        preferencesDataSource.putPreference(Constant.KEY_TARGET_CURRENCY, code)
    }

    override fun readSourceCurrencyCode(): Flow<CurrencyCode> {
        return preferencesDataSource
            .getPreference(Constant.KEY_SOURCE_CURRENCY, Constant.DEFAULT_SOURCE_CURRENCY)
            .map { code ->
                CurrencyCode.valueOf(code)
            }
    }

    override fun readTargetCurrencyCode(): Flow<CurrencyCode> {
        return preferencesDataSource
            .getPreference(Constant.KEY_TARGET_CURRENCY, Constant.DEFAULT_TARGET_CURRENCY)
            .map { code ->
                CurrencyCode.valueOf(code)
            }
    }
}