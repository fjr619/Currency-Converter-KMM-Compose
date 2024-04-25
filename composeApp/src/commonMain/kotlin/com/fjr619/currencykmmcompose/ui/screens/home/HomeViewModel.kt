package com.fjr619.currencykmmcompose.ui.screens.home

import androidx.lifecycle.ViewModel
import com.fjr619.currencykmmcompose.domain.repository.CurrencyRepository

class HomeViewModel(
    private val currencyRepository: CurrencyRepository
): ViewModel() {

    init {
        println("INI HOME VM")
    }
}