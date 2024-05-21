package com.fjr619.currencykmmcompose.di

import com.fjr619.currencykmmcompose.data.local.datastore.DataStoreProvider
import com.fjr619.currencykmmcompose.data.local.datastore.DataStoreProviderImpl
import com.fjr619.currencykmmcompose.ui.screens.home.HomeViewModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<DataStoreProvider> { DataStoreProviderImpl(androidContext()) }
    viewModel { HomeViewModel(get()) }
}

actual fun httpClientEngine(): HttpClientEngine = Android.create()