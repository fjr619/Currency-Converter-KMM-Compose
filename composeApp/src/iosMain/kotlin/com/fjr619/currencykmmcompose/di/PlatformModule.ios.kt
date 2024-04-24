package com.fjr619.currencykmmcompose.di

import com.fjr619.currencykmmcompose.data.local.datastore.DataStoreProvider
import com.fjr619.currencykmmcompose.data.local.datastore.DataStoreProviderImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<DataStoreProvider> { DataStoreProviderImpl() }
}
