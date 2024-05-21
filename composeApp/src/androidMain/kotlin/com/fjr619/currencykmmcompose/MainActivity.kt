package com.fjr619.currencykmmcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ChangeSystemBarsTheme(!isSystemInDarkTheme())
            App(
                darkTheme = isSystemInDarkTheme()
            )
        }
    }
}

@Composable
private fun ComponentActivity.ChangeSystemBarsTheme(lightTheme: Boolean) {
    val barColor = Color.Transparent.toArgb()
    LaunchedEffect(lightTheme) {
        println("lightTheme $lightTheme")
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                barColor,
            ),
            navigationBarStyle = SystemBarStyle.dark(
                barColor,
            ),
        )
    }
}
