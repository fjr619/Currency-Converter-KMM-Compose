package com.fjr619.currencykmmcompose

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform