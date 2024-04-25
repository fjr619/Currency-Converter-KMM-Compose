package com.fjr619.currencykmmcompose.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fjr619.currencykmmcompose.ui.ViewModelFac


@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel = ViewModelFac.getHomeViewModel()
    }
}