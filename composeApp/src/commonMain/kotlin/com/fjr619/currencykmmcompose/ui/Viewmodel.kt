package com.fjr619.currencykmmcompose.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fjr619.currencykmmcompose.ui.screens.home.HomeViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal object ViewModelFac : KoinComponent {
    @Composable
    fun getHomeViewModel(): HomeViewModel = viewModel { HomeViewModel(get()) }
}