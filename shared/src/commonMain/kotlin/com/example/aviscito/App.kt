package com.example.aviscito

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.aviscito.di.appModule
import com.example.aviscito.ui.PillListScreen
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
fun App() {
     KoinApplication(configuration = koinConfiguration { modules(appModule) }) {
        MaterialTheme {
            PillListScreen()
        }
    }
}