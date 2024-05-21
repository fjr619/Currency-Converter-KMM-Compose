package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.model.CurrencyType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberCurrencyPickerState(
    currencyList: List<Currency>,
    currencyType: CurrencyType,
): CurrencyPickerState {
    val density = LocalDensity.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    return remember {
        CurrencyPickerState(
            currencyList = currencyList,
            currencyType = currencyType,
            sheetState = sheetState,
            density = density
        )
    }
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
data class CurrencyPickerState internal constructor(
    val currencyList: List<Currency> = listOf(),
    val currencyType: CurrencyType,
    val sheetState: SheetState,
    private val density: Density
) {
    private val allCurrencies = mutableStateListOf<Currency>().apply { addAll(currencyList) }
    var searchQuery by mutableStateOf("")
        private set

    var selectedCurrencyCode by mutableStateOf(currencyType.code)
        private set

    var searchSize by mutableStateOf(IntSize.Zero)
        private set

    var buttonContainerSize by mutableStateOf(IntSize.Zero)
        private set

    fun clearAndAddCurrencies(newCurrencies: List<Currency>) {
        allCurrencies.clear()
        allCurrencies.addAll(newCurrencies)
    }

    fun getAllCurrencies() = allCurrencies

    fun updateSearchQuety(query: String) {
        searchQuery = query
    }

    fun updateSelectedCurrnecyCode(new: CurrencyCode) {
        selectedCurrencyCode = new
    }

    fun updateSearchSize(size : IntSize) {
        searchSize = size
    }

    fun updateButtonContainerSize(size: IntSize) {
        buttonContainerSize = size
    }

    fun paddingTopItemsContainer(): Dp = with(density) { (searchSize.height * 2).toDp() + 5.dp }

    fun offsetItemsContainer(): IntOffset = IntOffset(
        x = 0,
        y = -buttonContainerSize.height
    )

    fun offsetButtonsContainer(): IntOffset =  IntOffset(
        x = 0,
        y = -sheetState.requireOffset().toInt()
    )
}