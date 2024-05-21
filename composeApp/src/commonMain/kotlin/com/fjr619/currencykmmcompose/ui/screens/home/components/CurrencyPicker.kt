package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.ui.theme.textColor
import org.jetbrains.compose.resources.painterResource

val LocalCurrencyPickerState = compositionLocalOf<CurrencyPickerState> { error("ERROR") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPicker(
    currencyPickerState: CurrencyPickerState,
    onSelect: (CurrencyCode) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = onDismiss,
        sheetState = currencyPickerState.sheetState
    ) {
        // Sheet content
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.BottomEnd
        ) {

            CompositionLocalProvider(LocalCurrencyPickerState provides currencyPickerState) {
                CurrencyCodePickerItemContainer()
                SearchBar()
                ButtonContainer(
                    onSelect = onSelect,
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
fun BoxScope.CurrencyCodePickerItemContainer() {
    val currencyPickerState: CurrencyPickerState = LocalCurrencyPickerState.current
    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex =
        currencyPickerState.currencyList.indexOf(currencyPickerState.currencyList.find { it.code == currencyPickerState.selectedCurrencyCode.name })
    )

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .padding(
                top = currencyPickerState.paddingTopItemsContainer(),
                bottom = 16.dp
            )
            .padding(horizontal = 16.dp)
            .align(Alignment.TopCenter)
            .offset { currencyPickerState.offsetItemsContainer() },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = currencyPickerState.getAllCurrencies(),
            key = { it.code }
        ) { currency ->
            CurrencyCodePickerView(
                code = CurrencyCode.valueOf(currency.code),
                isSelected = currencyPickerState.selectedCurrencyCode.name == currency.code,
                onSelect = { currencyPickerState.updateSelectedCurrencyCode(it) }
            )
        }
    }
}

@Composable
fun CurrencyCodePickerView(
    code: CurrencyCode,
    isSelected: Boolean,
    onSelect: (CurrencyCode) -> Unit
) {
    val saturation = remember { Animatable(if (isSelected) 1f else 0f) }

    LaunchedEffect(isSelected) {
        saturation.animateTo(if (isSelected) 1f else 0f)
    }

    val colorMatrix = remember(saturation.value) {
        ColorMatrix().apply {
            setToSaturation(saturation.value)
        }
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.5f,
        animationSpec = tween(durationMillis = 300)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .clickable { onSelect(code) }
            .then(
                if (isSelected) {
                    Modifier.background(Color.LightGray.copy(alpha = if (isSystemInDarkTheme()) 0.2f else 0.3f))
                } else {
                    Modifier
                }
            )
            .padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(code.flag),
                contentDescription = "Currency Flag",
                colorFilter = ColorFilter.colorMatrix(colorMatrix)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.alpha(animatedAlpha),
                text = "${code.name} (${code.country})",
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
    }
}

@Composable
fun BoxScope.SearchBar() {
    val currencyPickerState: CurrencyPickerState = LocalCurrencyPickerState.current
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(size = 16.dp))
            .onGloballyPositioned {
                currencyPickerState.updateSearchSize(it.size)
            },
        value = currencyPickerState.searchQuery,
        onValueChange = { query ->
            currencyPickerState.updateSearchQuery(query.uppercase())
            if (currencyPickerState.searchQuery.isNotEmpty()) {
                val filteredCurrencies = currencyPickerState.currencyList.filter {
                    it.code.contains(
                        currencyPickerState.searchQuery,
                        true
                    ) or CurrencyCode.valueOf(it.code).country.contains(
                        currencyPickerState.searchQuery,
                        true
                    )
                }

                if (filteredCurrencies.size == 1) {
                    currencyPickerState.updateSelectedCurrencyCode(
                        CurrencyCode.valueOf(filteredCurrencies[0].code)
                    )
                }

                currencyPickerState.clearAndAddCurrencies(filteredCurrencies)
            } else {
                currencyPickerState.clearAndAddCurrencies(currencyPickerState.currencyList)
            }
        },
        placeholder = {
            Text(
                text = "Search here",
                color = textColor.copy(alpha = 0.38f),
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )
        },
        singleLine = true,
        textStyle = TextStyle(
            color = textColor,
            fontSize = MaterialTheme.typography.bodySmall.fontSize
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = textColor.copy(alpha = 0.1f),
            unfocusedContainerColor = textColor.copy(alpha = 0.1f),
            disabledContainerColor = textColor.copy(alpha = 0.1f),
            errorContainerColor = textColor.copy(alpha = 0.1f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = textColor,
        )
    )
}

@Composable
fun ButtonContainer(
    onSelect: (CurrencyCode) -> Unit,
    onDismiss: () -> Unit
) {
    val currencyPickerState: CurrencyPickerState = LocalCurrencyPickerState.current

    Row(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .height(50.dp)
            .offset { currencyPickerState.offsetButtonsContainer() }.onSizeChanged {
                currencyPickerState.updateButtonContainerSize(it)
            },
    ) {
        TextButton(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f),
            shape = RectangleShape,
            onClick = {
                onDismiss()
            }) {
            Text(text = "Cancel", color = MaterialTheme.colorScheme.outline)
        }

        TextButton(
            modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f),
            shape = RectangleShape,
            onClick = {
                onSelect(currencyPickerState.selectedCurrencyCode)
            }) {
            Text(text = "Select", color = MaterialTheme.colorScheme.primary)
        }
    }
}
