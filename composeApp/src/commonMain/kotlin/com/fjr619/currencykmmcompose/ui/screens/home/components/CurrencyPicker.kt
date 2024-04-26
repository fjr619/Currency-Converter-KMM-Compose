package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.model.CurrencyType
import com.fjr619.currencykmmcompose.ui.screens.home.HomeEvent
import com.fjr619.currencykmmcompose.ui.theme.surfaceColor
import com.fjr619.currencykmmcompose.ui.theme.textColor
import org.jetbrains.compose.resources.painterResource

class OffsetWrapper(var offset: Float = 0f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPicker(
    currencyList: List<Currency>,
    currencyType: CurrencyType,
    onEvent: (HomeEvent) -> Unit,
    onDismiss: () -> Unit
) {

    val allCurrencies = remember(key1 = currencyList) {
        mutableStateListOf<Currency>().apply { addAll(currencyList) }
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCurrencyCode by remember(currencyType) {
        mutableStateOf(currencyType.code)
    }

    BasicAlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(surfaceColor)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(size = 16.dp)),
                    value = searchQuery,
                    onValueChange = { query ->
                        searchQuery = query.uppercase()

                        if (query.isNotEmpty()) {
                            val filteredCurrencies = currencyList.filter {
                                it.code.contains(query.uppercase())
                            }
                            allCurrencies.clear()
                            allCurrencies.addAll(filteredCurrencies)
                        } else {
                            allCurrencies.clear()
                            allCurrencies.addAll(currencyList)
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

                Spacer(modifier = Modifier.height(20.dp))

                if (allCurrencies.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = allCurrencies,
                            key = { it.code }
                        ) { currency ->
                            CurrencyCodePickerView(
                                code = CurrencyCode.valueOf(currency.code),
                                isSelected = selectedCurrencyCode.name == currency.code,
                                onSelect = { selectedCurrencyCode = it }
                            )
                        }
                    }
                } else {
                    //TODO
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onDismiss()
                    }) {
                        Text(
                            text = "Cancel",
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(onClick = {
                        onEvent(
                            HomeEvent.SaveSelectedCurrencyCode(
                                currencyType,
                                selectedCurrencyCode
                            )
                        )
                    }) {
                        Text(
                            text = "Confirm",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    )
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
                text = code.name,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
        CurrencyCodeSelector(isSelected = isSelected)
    }
}

@Composable
private fun CurrencyCodeSelector(isSelected: Boolean = false) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else textColor.copy(alpha = 0.1f),
        animationSpec = tween(durationMillis = 300)
    )
    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(animatedColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Icons.Default.Check,
                contentDescription = "Checkmark icon",
                tint = surfaceColor
            )
        }
    }
}
