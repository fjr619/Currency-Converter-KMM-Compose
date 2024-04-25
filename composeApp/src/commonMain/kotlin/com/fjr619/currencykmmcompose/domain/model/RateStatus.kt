package com.fjr619.currencykmmcompose.domain.model

import androidx.compose.ui.graphics.Color
import com.fjr619.currencykmmcompose.ui.theme.freshColor
import com.fjr619.currencykmmcompose.ui.theme.staleColor

enum class RateStatus(
    val title: String,
    val color: Color
) {
    Idle(
        title = "Rates",
        color = Color.White
    ),
    Fresh(
        title = "Fresh rates",
        color = freshColor
    ),
    Stale(
        title = "Rates are not fresh",
        color = staleColor
    )
}