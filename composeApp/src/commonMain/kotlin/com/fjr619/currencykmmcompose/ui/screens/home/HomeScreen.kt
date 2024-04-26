package com.fjr619.currencykmmcompose.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjr619.currencykmmcompose.domain.model.CurrencyType
import com.fjr619.currencykmmcompose.ui.ViewModelFac
import com.fjr619.currencykmmcompose.ui.screens.home.components.CurrencyPicker
import com.fjr619.currencykmmcompose.ui.screens.home.components.HomeHeader


@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel = ViewModelFac.getHomeViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()
        var dialogOpened by rememberSaveable { mutableStateOf(false) }
        var selectedCurrencyType: CurrencyType by remember {
            mutableStateOf(CurrencyType.None)
        }

        if (dialogOpened && selectedCurrencyType != CurrencyType.None) {
            CurrencyPicker(
                currencyList = state.currencyRates,
                currencyType = selectedCurrencyType,
                onEvent = {
                    viewModel.onEvent(it)
                    dialogOpened = false
                    selectedCurrencyType = CurrencyType.None
                },
                onDismiss = {
                    dialogOpened = false
                    selectedCurrencyType = CurrencyType.None
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeHeader(
                state = state,
                onEvent = viewModel::onEvent,
                onCurrencyTypeSelect = {
                    println("currecnyType $it")
                    dialogOpened = true
                    selectedCurrencyType = it
                }
            )
        }
    }
}