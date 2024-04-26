package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyType
import com.fjr619.currencykmmcompose.domain.model.RateStatus
import com.fjr619.currencykmmcompose.ui.screens.home.HomeEvent
import com.fjr619.currencykmmcompose.ui.screens.home.HomeUiState
import com.fjr619.currencykmmcompose.ui.theme.headerColor

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onEvent: (HomeEvent) -> Unit,
    onCurrencyTypeSelect: (CurrencyType) -> Unit
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(headerColor)
            .padding(16.dp)
    ) {
        RatesStatus(
            status = state.rateState,
            onRatesRefresh = {}
        )
        Spacer(modifier = Modifier.height(12.dp))
        CurrencyInputs(
            source = state.sourceCurrency,
            target = state.targetCurrency,
            onSwitch = {
                onEvent(HomeEvent.SwitchCurrencies)
            },
            onCurrencyTypeSelect = onCurrencyTypeSelect
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}