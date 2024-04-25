package com.fjr619.currencykmmcompose.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjr619.currencykmmcompose.domain.model.RateStatus
import com.fjr619.currencykmmcompose.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

            is HomeEvent.SaveSourceCurrencyCode -> {
                saveSourceCurrencyCode(event.code)
            }

            is HomeEvent.SaveTargetCurrencyCode -> {
                saveTargetCurrencyCode(event.code)
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
            currencyRepository.readSourceCurrencyCode().collect { currencyCode ->
               val selectedCurrency = _state.value.currencyRates.find { it.code == currencyCode.name }
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

    private fun readTargetCurrency() {
        viewModelScope.launch {
            currencyRepository.readTargetCurrencyCode().collect { currencyCode ->
                val selectedCurrency = _state.value.currencyRates.find { it.code == currencyCode.name }
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

    private fun saveTargetCurrencyCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveTargetCurrencyCode(code)
        }
    }


}