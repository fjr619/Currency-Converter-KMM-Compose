package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.fjr619.currencykmmcompose.domain.model.RateStatus
import currencykmmcompose.composeapp.generated.resources.Res
import currencykmmcompose.composeapp.generated.resources.refresh_ic
import org.jetbrains.compose.resources.painterResource

@Composable
fun RatesStatus(
    status: RateStatus,
    onRatesRefresh: () -> Unit
) {

    val infiniteTransition = rememberInfiniteTransition()
    var animationStarted by remember { mutableStateOf(false) }
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween<Float>(
                durationMillis = 1000,
                easing = LinearEasing,
            ),
        )
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = status.title,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = status.color
        )

        LaunchedEffect(status) {
            animationStarted = status == RateStatus.Loading

        }

        AnimatedVisibility(
            visible = when (status) {
                RateStatus.Loading, RateStatus.Stale, RateStatus.Error -> true
                else -> false
            },
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            IconButton(
                onClick = dropUnlessResumed {
                    animationStarted = true
                    onRatesRefresh()
                },
                enabled = !animationStarted
            ) {
                Icon(
                    modifier = Modifier.size(24.dp)
                        .then(
                            if (animationStarted) {
                                Modifier.rotate(rotation)
                            } else {
                                Modifier
                            }
                        ),
                    painter = painterResource(Res.drawable.refresh_ic),
                    contentDescription = "Refresh Icon",
                    tint = status.color
                )
            }
        }
    }
}