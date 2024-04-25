package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.utils.enterTrantition
import com.fjr619.currencykmmcompose.utils.exitTransition
import com.fjr619.currencykmmcompose.utils.transitionSpec
import org.jetbrains.compose.resources.painterResource

@Composable
fun RowScope.CurrencyView(
    placeholder: String,
    currency: Currency?,
    onClick: (Currency) -> Unit
) {
    Column(
        modifier = Modifier.weight(1f)
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeholder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color.White.copy(alpha = 0.05f))
                .height(45.dp)
                .clickable {
                    currency?.let {
                        onClick(it)
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = currency != null,
                enter = enterTrantition(),
                exit = exitTransition()
            ) {
                currency?.let { data ->
                    AnimatedContent(
                        targetState = data,
                        transitionSpec = { transitionSpec() },
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(
                                    CurrencyCode.valueOf(data.code).flag
                                ),
                                tint = Color.Unspecified,
                                contentDescription = "Country Flag"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = CurrencyCode.valueOf(data.code).name,
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = Color.White
                            )
                        }

                    }
                }
            }
        }
    }
}