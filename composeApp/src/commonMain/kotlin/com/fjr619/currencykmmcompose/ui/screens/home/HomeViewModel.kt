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
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.FetchRates -> {
                fetchNewRates()
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
}