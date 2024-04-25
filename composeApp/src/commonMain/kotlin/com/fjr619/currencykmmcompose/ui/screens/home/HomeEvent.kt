package com.fjr619.currencykmmcompose.ui.screens.home

sealed class HomeEvent {
    data object FetchRates: HomeEvent()
}