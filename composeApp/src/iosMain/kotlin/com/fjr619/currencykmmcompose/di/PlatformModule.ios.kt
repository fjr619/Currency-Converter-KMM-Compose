package com.fjr619.currencykmmcompose.di

import com.fjr619.currencykmmcompose.data.local.datastore.DataStoreProvider
import com.fjr619.currencykmmcompose.data.local.datastore.DataStoreProviderImpl
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<DataStoreProvider> { DataStoreProviderImpl() }
}

actual fun httpClientEngine(): HttpClientEngine = Darwin.create()