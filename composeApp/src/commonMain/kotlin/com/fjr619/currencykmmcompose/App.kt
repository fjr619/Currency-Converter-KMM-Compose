package com.fjr619.currencykmmcompose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjr619.currencykmmcompose.data.local.datastore.PreferencesDataSource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import currencykmmcompose.composeapp.generated.resources.Res
import currencykmmcompose.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import org.koin.compose.koinInject

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }

        val coroutineScope = rememberCoroutineScope()

        //example read and write datastore
        val a = getKoin().get<PreferencesDataSource>() //or koinInject<PreferencesDataSource>()
        val title by a.getTitle().collectAsStateWithLifecycle("", lifecycleOwner = LocalLifecycleOwner.current)

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
                coroutineScope.launch {
                    a.saveTitle("AAAA")
                }
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting $title")
                }
            }
        }
    }
}