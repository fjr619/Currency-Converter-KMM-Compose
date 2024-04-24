package com.fjr619.currencykmmcompose.di

import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSource
import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSourceImpl
import com.fjr619.currencykmmcompose.data.remote.RemoteDataSource
import com.fjr619.currencykmmcompose.data.remote.RemoteDataSourceImpl
import com.fjr619.currencykmmcompose.data.remote.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module{
    single<PreferencesDataSource> { PreferencesDataSourceImpl(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<HttpClientEngine> { httpClientEngine() }
    single<HttpClient> { createHttpClient(get()) }
}