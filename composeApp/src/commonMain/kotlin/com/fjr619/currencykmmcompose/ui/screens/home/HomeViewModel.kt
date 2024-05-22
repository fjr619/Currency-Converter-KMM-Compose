package com.fjr619.currencykmmcompose.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjr619.currencykmmcompose.domain.model.CurrencyType
import com.fjr619.currencykmmcompose.domain.model.RateStatus
import com.fjr619.currencykmmcompose.domain.repository.CurrencyRepository
import com.fjr619.currencykmmcompose.ui.screens.home.components.backKey
import com.fjr619.currencykmmcompose.ui.screens.home.components.clearKey
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val _updatedCurrencyValue: MutableStateFlow<String> = MutableStateFlow("")

    private lateinit var jobDataFresh: Job

    init {
        println("INI HOME VM")
        onEvent(HomeEvent.FetchRates)
        onEvent(HomeEvent.ReadSourceCurrencyCode)
        onEvent(HomeEvent.ReadTargetCurrencyCode)

        viewModelScope.launch {
            _updatedCurrencyValue.collect { value ->
                _state.update {
                    it.copy(
                        sourceCurrencyAmount = value,
                    )
                }
            }
        }

        viewModelScope.launch {
            state.collect {
                _state.update {
                    it.copy(
                        targetCurrencyAmount = convert(it.sourceCurrencyAmount).toString()
                    )
                }
            }
        }
    }


    private fun convert(amount: String): Double {
        val source = state.value.sourceCurrency?.value
        val target = state.value.targetCurrency?.value

        val exchangeRate = if (source != null && target != null) {
            target / source
        } else 1.0

        return if (amount.isEmpty()) 0.0
        else amount.toDouble() * exchangeRate
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

            is HomeEvent.NumberButtonClicked -> {
                updateCurrencyValue(event.value)
            }
        }
    }


    private fun fetchNewRates() {
        if (::jobDataFresh.isInitialized) {
            jobDataFresh.cancel()
        }

        jobDataFresh = viewModelScope.launch {
            _state.update {
                it.copy(
                    rateState = RateStatus.Loading
                )
            }

            currencyRepository.fetchNewRates().collectLatest { result ->
                if (result.isSuccess) {
                    _state.update {
                        it.copy(
                            currencyRates = result.getOrElse { listOf() }
                        )
                    }
                } else if (result.isFailure) {
                    _state.update {
                        it.copy(
                            rateState = RateStatus.Error
                        )
                    }
                }
            }

            currencyRepository.isDataFresh().collectLatest { fresh ->
                _state.update {
                    it.copy(
                        rateState = if (fresh) RateStatus.Fresh else RateStatus.Stale
                    )
                }
            }
        }
    }

    fun consumeAnimatedResult() {
        updateAnimatedResult(false)
    }

    private fun updateAnimatedResult(boolean: Boolean) {
        _state.update {
            it.copy(
                animatedResult = boolean
            )
        }
    }

    private fun saveSourceCurrencyCode(code: String) {
        viewModelScope.launch {
            currencyRepository.saveSourceCurrencyCode(code)
            updateAnimatedResult(true)
        }
    }

    private fun readSourceCurrency() {
        viewModelScope.launch {
            currencyRepository.readSourceCurrencyCode().collectLatest { currencyCode ->
                state.collect { homeuiState ->
                    val selectedCurrency =
                        homeuiState.currencyRates.find { it.code == currencyCode.name }
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
            updateAnimatedResult(true)
        }
    }

    private fun readTargetCurrency() {
        viewModelScope.launch {
            currencyRepository.readTargetCurrencyCode().collectLatest { currencyCode ->
                state.collect { homeuiState ->
                    val selectedCurrency =
                        homeuiState.currencyRates.find { it.code == currencyCode.name }
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

    private fun updateCurrencyValue(value: String) {
        val currentCurrencyValue = state.value.sourceCurrencyAmount
        _updatedCurrencyValue.update {
            when (value) {
                clearKey -> "0"
                else -> {
                    if (value != backKey) {
                        if (currentCurrencyValue == "0") value else {
                            (currentCurrencyValue + value).run {
                                if (this.length > 9) {
                                    this.substring(0, 9)
                                } else {
                                    this
                                }
                            }
                        }
                    } else {
                        if (currentCurrencyValue.length >= 0) {
                            currentCurrencyValue.dropLast(1)
                        } else {
                            "0"
                        }

                    }

                }

            }
        }
    }
}