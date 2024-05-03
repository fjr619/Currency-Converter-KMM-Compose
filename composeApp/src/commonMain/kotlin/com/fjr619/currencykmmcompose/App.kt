package com.fjr619.currencykmmcompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fjr619.currencykmmcompose.ui.screens.home.HomeScreen
import com.fjr619.currencykmmcompose.ui.theme.AppTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App(
    darkTheme: Boolean,
    dynamicColor: Boolean = false,
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = dynamicColor
    ) {
        val navController: NavHostController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "Home"
        ) {
            composable(
                route = "Home"
            ) {
                HomeScreen()
            }
        }

    }
}