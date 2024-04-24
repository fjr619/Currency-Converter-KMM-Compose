package com.fjr619.currencykmmcompose.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        platformModule(),
        dataModule
    )
}

//called by iOS etc
fun initKoin() = initKoin { }