package com.fjr619.currencykmmcompose.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjr619.currencykmmcompose.ui.ViewModelFac
import kotlinx.datetime.Clock


@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val viewModel = ViewModelFac.getHomeViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()


        Text("Textaaaa rate status ${state.rateState}")
    }
}