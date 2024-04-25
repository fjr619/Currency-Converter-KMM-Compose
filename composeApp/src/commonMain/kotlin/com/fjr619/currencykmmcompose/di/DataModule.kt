package com.fjr619.currencykmmcompose.di

import com.fjr619.currencykmmcompose.data.local.database.LocalDataSource
import com.fjr619.currencykmmcompose.data.local.database.LocalDataSourceImpl
import com.fjr619.currencykmmcompose.data.local.database.model.Currency
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
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module{
    single<PreferencesDataSource> { PreferencesDataSourceImpl(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<HttpClientEngine> { httpClientEngine() }
    single<HttpClient> { createHttpClient(get()) }
    single<Configuration> {
        RealmConfiguration.Builder(
            schema = setOf(Currency::class)
        ).compactOnLaunch().build()
    }
    single<Realm> {
        Realm.open(get())
    }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
}