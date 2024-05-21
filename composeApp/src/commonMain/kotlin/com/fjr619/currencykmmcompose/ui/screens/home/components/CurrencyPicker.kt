package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.model.CurrencyType
import com.fjr619.currencykmmcompose.ui.theme.surfaceColor
import com.fjr619.currencykmmcompose.ui.theme.textColor
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

class OffsetWrapper(var offset: Float = 0f)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPicker(
    currencyList: List<Currency>,
    currencyType: CurrencyType,
    onSelect: (CurrencyCode) -> Unit,
    onDismiss: () -> Unit
) {

    val allCurrencies = remember(key1 = currencyList) {
        mutableStateListOf<Currency>().apply { addAll(currencyList) }
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCurrencyCode by remember(currencyType) {
        mutableStateOf(currencyType.code)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    var buttonSize by remember {
        mutableStateOf(IntSize.Zero)
    }

    var searchSize by remember {
        mutableStateOf(IntSize.Zero)
    }


    val scrollState = rememberLazyListState(
        initialFirstVisibleItemIndex = currencyList.indexOf(currencyList.find { it.code == selectedCurrencyCode.name })

    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        // Sheet content
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.BottomEnd
        ) {

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .padding(
                        top = with(density) { (searchSize.height * 2).toDp() + 5.dp },
                        bottom = 16.dp
                    )
                    .padding(horizontal = 16.dp)
                    .align(Alignment.TopCenter)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = -buttonSize.height
                        )
                    },
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

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(size = 16.dp))
                    .onGloballyPositioned {
                        searchSize = it.size
                    },
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query.uppercase()
                    if (searchQuery.isNotEmpty()) {
                        val filteredCurrencies = currencyList.filter {
                            it.code.contains(
                                searchQuery,
                                true
                            ) or CurrencyCode.valueOf(it.code).country.contains(searchQuery, true)
                        }

                        if (filteredCurrencies.size == 1) {
                            selectedCurrencyCode = CurrencyCode.valueOf(filteredCurrencies[0].code)
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


            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .height(50.dp)
                    .offset {
                        IntOffset(
                            x = 0,
                            y = -sheetState.requireOffset().toInt()
                        )
                    }.onSizeChanged {
                        buttonSize = it
                    },
            ) {
                TextButton(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f),
                    shape = RectangleShape,
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    }) {
                    Text(text = "Cancel", color = MaterialTheme.colorScheme.outline)
                }

                TextButton(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().weight(1f),
                    shape = RectangleShape,
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onSelect(selectedCurrencyCode)
                        }
                    }) {
                    Text(text = "Select", color = MaterialTheme.colorScheme.primary)
                }
            }

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
//        CurrencyCodeSelector(isSelected = isSelected)
    }
}

//@Composable
//private fun CurrencyCodeSelector(isSelected: Boolean = false) {
//    val animatedColor by animateColorAsState(
//        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else textColor.copy(alpha = 0.1f),
//        animationSpec = tween(durationMillis = 300)
//    )
//    Box(
//        modifier = Modifier
//            .size(18.dp)
//            .clip(CircleShape)
//            .background(animatedColor),
//        contentAlignment = Alignment.Center
//    ) {
//        if (isSelected) {
//            Icon(
//                modifier = Modifier.size(12.dp),
//                imageVector = Icons.Default.Check,
//                contentDescription = "Checkmark icon",
//                tint = surfaceColor
//            )
//        }
//    }
//}
