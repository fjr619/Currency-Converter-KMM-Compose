package com.fjr619.currencykmmcompose.ui.screens.home

import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.RateStatus

data class HomeUiState(
    val rateState: RateStatus = RateStatus.Idle,
    val loading: Boolean = false,
    val currencyRates: List<Currency> = emptyList()
) {
}