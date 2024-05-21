package com.fjr619.currencykmmcompose.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjr619.currencykmmcompose.domain.model.CurrencyType
import com.fjr619.currencykmmcompose.ui.ViewModelFac
import com.fjr619.currencykmmcompose.ui.screens.home.components.CurrencyPicker
import com.fjr619.currencykmmcompose.ui.screens.home.components.HomeHeader
import com.fjr619.currencykmmcompose.ui.screens.home.components.KeyboardButton
import com.fjr619.currencykmmcompose.ui.theme.headerColor


@OptIn(ExperimentalMaterial3Api::class)
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
                onSelect = {
                    viewModel.onEvent(
                        HomeEvent.SaveSelectedCurrencyCode(
                            selectedCurrencyType, it
                        )
                    )
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

            val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "C")
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.Center
            ) {
                items(keys,
                    span = { key ->
                        val spanCount = if (key == "C") 2 else 1
                        GridItemSpan(spanCount)
                    }
                ) { key ->
                    KeyboardButton(
                        modifier = Modifier.height(100.dp),
                        key = key,
                        backgroundColor = if (key == "C") headerColor
                        else headerColor,
                        onClick = {
                            viewModel.onEvent(HomeEvent.NumberButtonClicked(key))
                        }
                    )
                }
            }
        }


    }
}