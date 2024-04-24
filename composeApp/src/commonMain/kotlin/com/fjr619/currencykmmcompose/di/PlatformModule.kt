package com.fjr619.currencykmmcompose.di

import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module

expect fun platformModule(): Module
expect fun httpClientEngine(): HttpClientEngine