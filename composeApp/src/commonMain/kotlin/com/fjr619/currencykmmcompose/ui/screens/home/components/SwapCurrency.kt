package com.fjr619.currencykmmcompose.ui.screens.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import com.fjr619.currencykmmcompose.ui.theme.headerColor
import currencykmmcompose.composeapp.generated.resources.Res
import currencykmmcompose.composeapp.generated.resources.switch_ic
import org.jetbrains.compose.resources.painterResource

@Composable
fun SwapCurrency(
    onSwap: () -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }
    val animatedRotation by animateFloatAsState(
        targetValue = if (animationStarted) 270f else 90f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier.height(IntrinsicSize.Max).fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.White))
        IconButton(
            modifier = Modifier
                .padding(top = 6.dp, bottom = 4.dp)
                .background(headerColor)
                .rotate(animatedRotation),
            onClick = dropUnlessResumed {
                animationStarted = !animationStarted
                onSwap()
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Switch Icon",
                tint = Color.White
            )
        }
    }
}