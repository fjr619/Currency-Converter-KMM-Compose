package com.fjr619.currencykmmcompose.ui.screens.home

sealed class HomeEvent {
    data object FetchRates: HomeEvent()
    data class SaveSourceCurrencyCode(val code: String): HomeEvent()
    data class SaveTargetCurrencyCode(val code: String): HomeEvent()

    data object ReadSourceCurrencyCode: HomeEvent()
    data object ReadTargetCurrencyCode: HomeEvent()
}