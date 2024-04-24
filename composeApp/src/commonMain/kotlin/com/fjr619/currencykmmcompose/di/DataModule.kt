package com.fjr619.currencykmmcompose.di

import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSource
import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSourceImpl
import org.koin.dsl.module

val dataModule = module{
    single<PreferencesDataSource> { PreferencesDataSourceImpl(get()) }
}