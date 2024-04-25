package com.fjr619.currencykmmcompose.di

import com.fjr619.currencykmmcompose.data.local.database.LocalDataSource
import com.fjr619.currencykmmcompose.data.local.database.LocalDataSourceImpl
import com.fjr619.currencykmmcompose.data.local.database.model.CurrencyEntity
import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSource
import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSourceImpl
import com.fjr619.currencykmmcompose.data.remote.RemoteDataSource
import com.fjr619.currencykmmcompose.data.remote.RemoteDataSourceImpl
import com.fjr619.currencykmmcompose.data.remote.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.realm.kotlin.Configuration
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

val dataModule = module{
    single<PreferencesDataSource> { PreferencesDataSourceImpl(get()) }
    single<HttpClientEngine> { httpClientEngine() }
    single<HttpClient> { createHttpClient(get()) }
    single<Configuration> {
        RealmConfiguration.Builder(
            schema = setOf(CurrencyEntity::class)
        ).compactOnLaunch().build()
    }
    single<Realm> {
        Realm.open(get())
    }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
}