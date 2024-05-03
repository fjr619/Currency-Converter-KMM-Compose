package com.fjr619.currencykmmcompose.ui.screens.home

import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.model.RateStatus
import com.fjr619.currencykmmcompose.utils.Constant

data class HomeUiState(
    val rateState: RateStatus = RateStatus.Idle,
    val loading: Boolean = false,
    val currencyRates: List<Currency> = emptyList(),
    val sourceCurrency: Currency? = null,
    val targetCurrency: Currency? = null,

    val sourceCurrencyAmount: String = "0",
    val targetCurrencyAmount: String = "0"
) {
}