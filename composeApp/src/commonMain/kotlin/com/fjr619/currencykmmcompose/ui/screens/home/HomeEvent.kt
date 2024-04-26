package com.fjr619.currencykmmcompose.ui.screens.home

import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.model.CurrencyType

sealed class HomeEvent {
    data object FetchRates : HomeEvent()
    data object SwitchCurrencies : HomeEvent()

    data class SaveSelectedCurrencyCode(
        val currencyType: CurrencyType,
        val currencyCode: CurrencyCode
    ) : HomeEvent()

    data object ReadSourceCurrencyCode : HomeEvent()
    data object ReadTargetCurrencyCode : HomeEvent()
}