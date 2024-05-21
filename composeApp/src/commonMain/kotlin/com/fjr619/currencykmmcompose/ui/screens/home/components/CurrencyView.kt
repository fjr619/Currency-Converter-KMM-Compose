package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.fjr619.currencykmmcompose.domain.model.AmountInputType
import com.fjr619.currencykmmcompose.domain.model.Currency
import com.fjr619.currencykmmcompose.domain.model.CurrencyCode
import com.fjr619.currencykmmcompose.utils.enterTrantition
import com.fjr619.currencykmmcompose.utils.exitTransition
import org.jetbrains.compose.resources.painterResource

@Composable
fun CurrencyView(
    placeholder: String,
    amount: String,
    currency: Currency?,
    amountInputType: AmountInputType,
    onClick: (Currency) -> Unit,
    animatedResult: Boolean = false,
    consumeAnimatedResult: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
//        modifier = Modifier.weight(1f)
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeholder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color.White.copy(alpha = 0.05f)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .background(Color.White.copy(alpha = 0.05f))
                    .clickable {
                        currency?.let {
                            onClick(it)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = currency != null,
                    enter = enterTrantition(),
                    exit = exitTransition()
                ) {
                    currency?.let { data ->
                        AnimatedContent(
                            targetState = data,
                            transitionSpec = {
                                slideInHorizontally(
                                    initialOffsetX = { fullWidth -> fullWidth },
                                ) togetherWith slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -fullWidth },
                                )
                            }
                        ) {

                            Row(
                                modifier = Modifier.padding(8.dp),
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

            AmountInput(
                amountInputType = amountInputType,
                amount = amount,
                animatedResult = animatedResult,
                consumeAnimatedResult = consumeAnimatedResult
            )
        }
    }
}