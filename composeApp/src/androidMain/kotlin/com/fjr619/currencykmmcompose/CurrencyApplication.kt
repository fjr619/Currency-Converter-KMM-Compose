package com.fjr619.currencykmmcompose

import android.app.Application
import com.fjr619.currencykmmcompose.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class CurrencyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            androidContext(this@CurrencyApplication)
        }
    }
}