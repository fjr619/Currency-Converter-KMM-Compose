package com.fjr619.currencykmmcompose.utils

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith

fun enterTrantition() = scaleIn(tween(durationMillis = 400)) + fadeIn(tween(durationMillis = 800))
fun exitTransition() = scaleOut(tween(durationMillis = 400)) + fadeOut(tween(durationMillis = 800))
fun transitionSpec() = enterTrantition() togetherWith exitTransition()