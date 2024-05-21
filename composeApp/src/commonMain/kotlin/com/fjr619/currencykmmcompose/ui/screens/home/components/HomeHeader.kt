package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.AmountInputType
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.domain.model.CurrencyType
import com.fjr619.currencykmmcompose.ui.screens.home.HomeEvent
import com.fjr619.currencykmmcompose.ui.screens.home.HomeUiState
import com.fjr619.currencykmmcompose.ui.theme.headerColor
import currencykmmcompose.composeapp.generated.resources.Res
import currencykmmcompose.composeapp.generated.resources.switch_ic
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    animatedResult: Boolean,
    onEvent: (HomeEvent) -> Unit,
    onCurrencyTypeSelect: (CurrencyType) -> Unit,
    consumeAnimatedResult: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(headerColor)
            .padding(24.dp)
    ) {
        RatesStatus(
            status = state.rateState,
            onRatesRefresh = { TODO() }
        )
        Spacer(modifier = Modifier.height(12.dp))
//        CurrencyChoices(
//            source = state.sourceCurrency,
//            target = state.targetCurrency,
//            onSwitch = {
//                onEvent(HomeEvent.SwitchCurrencies)
//            },
//            onCurrencyTypeSelect = onCurrencyTypeSelect
//        )
//        Spacer(modifier = Modifier.height(12.dp))

        CurrencyView(
            placeholder = "from",
            currency = state.sourceCurrency,
            amount = state.sourceCurrencyAmount,
            amountInputType = AmountInputType.SOURCE,
            onClick = {
                onCurrencyTypeSelect(
                    CurrencyType.Source(CurrencyCode.valueOf(it.code))
                )
            }
        )

        var animationStarted by remember { mutableStateOf(false) }
        val animatedRotation by animateFloatAsState(
            targetValue = if (animationStarted) 180f else 0f,
            animationSpec = tween(durationMillis = 500)
        )
        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 4.dp)
                .rotate(animatedRotation),
            onClick = {
                animationStarted = !animationStarted
                onEvent(HomeEvent.SwitchCurrencies)
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Switch Icon",
                tint = Color.White
            )
        }

        CurrencyView(
            placeholder = "to",
            amountInputType = AmountInputType.TARGET,
            currency = state.targetCurrency,
            amount = state.targetCurrencyAmount,
            animatedResult = animatedResult,
            onClick = {
                onCurrencyTypeSelect(
                    CurrencyType.Source(CurrencyCode.valueOf(it.code))
                )
            },
            consumeAnimatedResult = consumeAnimatedResult
        )

//        AmountInput(
//            amountInputType = AmountInputType.SOURCE,
//            amount = state.sourceCurrencyAmount
//        )

//        Spacer(modifier = Modifier.height(12.dp))
//
//        CurrencyView(
//            placeholder = "to",
//            currency = state.targetCurrency,
//            onClick = {
//                onCurrencyTypeSelect(
//                    CurrencyType.Source(CurrencyCode.valueOf(it.code))
//                )
//            }
//        )
//
//        Spacer(modifier = Modifier.height(6.dp))
//
//        AmountInput(
//            amountInputType = AmountInputType.TARGET,
//            amount = state.targetCurrencyAmount,
//            animatedResult = animatedResult,
//            consumeAnimatedResult = consumeAnimatedResult
//        )
    }
}