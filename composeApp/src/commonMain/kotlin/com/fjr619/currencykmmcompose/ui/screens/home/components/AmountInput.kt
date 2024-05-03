package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.AmountInputType
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.utils.DecimalFormat
import com.fjr619.currencykmmcompose.utils.formatDecimalSeparator

@Composable
fun AmountInput(
    amountInputType: AmountInputType,
    currency: Currency?,
    amount: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(size = 8.dp))
                .animateContentSize()
                .height(54.dp),
            value = if(amount.isNotEmpty()) DecimalFormat().format(amount.toDouble()).formatDecimalSeparator() else "0",
            readOnly = true,
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White.copy(alpha = 0.05f),
                unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
                disabledContainerColor = Color.White.copy(alpha = 0.05f),
                errorContainerColor = Color.White.copy(alpha = 0.05f),
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