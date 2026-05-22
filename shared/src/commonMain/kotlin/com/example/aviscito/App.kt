package com.example.aviscito

import androidx.compose.runtime.Composable
import com.example.aviscito.di.appModule
import com.example.aviscito.navigation.AppShell
import com.example.aviscito.theme.AppTheme
import org.koin.compose.KoinApplication
import org.koin.dsl.koinConfiguration

@Composable
fun App() {
     KoinApplication(configuration = koinConfiguration { modules(appModule) }) {
        AppTheme {
            AppShell()
        }
    }
}