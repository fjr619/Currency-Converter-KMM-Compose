package com.fjr619.currencykmmcompose.di

import com.fjr619.currencykmmcompose.data.repository.CurrencyRepositoryImpl
import com.fjr619.currencykmmcompose.domain.repository.CurrencyRepository
import org.koin.dsl.module

val domainModule = module {
    factory<CurrencyRepository> { CurrencyRepositoryImpl(get(), get(), get()) }
}