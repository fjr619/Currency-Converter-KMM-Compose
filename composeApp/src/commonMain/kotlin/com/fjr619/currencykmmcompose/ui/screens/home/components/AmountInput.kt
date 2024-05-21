package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.AmountInputType
import com.fjr619.currencykmmcompose.utils.DecimalFormat
import com.fjr619.currencykmmcompose.utils.formatDecimalSeparator
import kotlinx.coroutines.delay

class DoubleConverter : TwoWayConverter<Double, AnimationVector1D> {
    override val convertFromVector: (AnimationVector1D) -> Double = { vector ->
        vector.value.toDouble()
    }
    override val convertToVector: (Double) -> AnimationVector1D = { value ->
        AnimationVector1D(value.toFloat())
    }
}

@Composable
fun AmountInput(
    amountInputType: AmountInputType,
    amount: String,
    animatedResult: Boolean = false,
    consumeAnimatedResult: (() -> Unit)? = null
) {
    val animatedAmount by animateValueAsState(
        targetValue = if (amount.isEmpty()) 0.0 else amount.toDouble(),
        animationSpec = tween(durationMillis = 300),
        typeConverter = DoubleConverter()
    )

    LaunchedEffect(amount) {
        if (consumeAnimatedResult != null && animatedResult) {
            delay(200)
            consumeAnimatedResult()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        horizontalAlignment = Alignment.End
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth()
                .animateContentSize()
                .height(54.dp),
            value = if (amount.isNotEmpty()) {
                val result =
                    DecimalFormat().format(if (animatedResult) animatedAmount else amount.toDouble())
                        .formatDecimalSeparator()

                result
            } else {
                "0"
            },
            readOnly = true,
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,

                unfocusedTextColor =
                if (amountInputType == AmountInputType.SOURCE) {
                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                } else {
                    Color.White
                },
                focusedTextColor = if (amountInputType == AmountInputType.SOURCE) {
                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inversePrimary
                } else {
                    Color.White
                }
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End
            )

        )
    }

}