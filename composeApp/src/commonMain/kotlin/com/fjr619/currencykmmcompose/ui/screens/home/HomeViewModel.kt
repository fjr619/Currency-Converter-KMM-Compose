package com.fjr619.currencykmmcompose.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.model.CurrencyType
import com.fjr619.currencykmmcompose.domain.model.RateStatus
import com.fjr619.currencykmmcompose.domain.repository.CurrencyRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        println("INI HOME VM")
        onEvent(HomeEvent.FetchRates)
        onEvent(HomeEvent.ReadSourceCurrencyCode)
        onEvent(HomeEvent.ReadTargetCurrencyCode)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.FetchRates -> {
                fetchNewRates()
            }

            is HomeEvent.ReadSourceCurrencyCode -> {
                readSourceCurrency()
            }

            is HomeEvent.ReadTargetCurrencyCode -> {
                readTargetCurrency()
            }

            is HomeEvent.SaveSelectedCurrencyCode -> {
                if (event.currencyType is CurrencyType.Source) {
                    saveSourceCurrencyCode(event.currencyCode.name)
                } else if (event.currencyType is CurrencyType.Target) {
                    saveTargetCurrencyCode(event.currencyCode.name)
                }
            }

            is HomeEvent.SwitchCurrencies -> {
                switchCurrencies()
            }
        }
    }

    private fun fetchNewRates() {
        viewModelScope.launch {
            _state.update {
                it.copy(loading = true)
            }

            currencyRepository.fetchNewRates(
                onSucceed = { list ->
                    _state.update {
                        it.copy(
                            loading = false,
                            currencyRates = list
                        )
                    }
                },

                onFailled = {
                    _state.update {
                        it.copy(
                            loading = false
                        )
                    }
                }
            )

            currencyRepository.isDataFresh().collect { fresh ->
                _state.update {
                    it.copy(
                        rateState = if (fresh) RateStatus.Fresh else RateStatus.Stale
                    )
                }
            }
        }
    }

    private fun saveSourceCurrencyCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveSourceCurrencyCode(code)
        }
    }

    private fun readSourceCurrency() {
        viewModelScope.launch {
            currencyRepository.readSourceCurrencyCode().collectLatest { currencyCode ->
                state.collect { homeuiState ->
                    val selectedCurrency = homeuiState.currencyRates.find { it.code == currencyCode.name }
                    selectedCurrency?.let { nonNullData ->
                        _state.update {
                            it.copy(
                                sourceCurrency = nonNullData
                            )
                        }
                    }
                }

            }
        }
    }

    private fun saveTargetCurrencyCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveTargetCurrencyCode(code)
        }
    }

    private fun readTargetCurrency() {
        viewModelScope.launch {
            currencyRepository.readTargetCurrencyCode().collectLatest { currencyCode ->
                state.collect { homeuiState ->
                    val selectedCurrency = homeuiState.currencyRates.find { it.code == currencyCode.name }
                    selectedCurrency?.let { nonNullData ->
                        _state.update {
                            it.copy(
                                targetCurrency = nonNullData
                            )
                        }
                    }
                }

            }
        }
    }

    private fun switchCurrencies() {
        viewModelScope.launch {
            val source = state.value.sourceCurrency
            val target = state.value.targetCurrency
            source?.let {
                saveTargetCurrencyCode(it.code)
            }

            target?.let {
                saveSourceCurrencyCode(it.code)
            }
        }
    }
}