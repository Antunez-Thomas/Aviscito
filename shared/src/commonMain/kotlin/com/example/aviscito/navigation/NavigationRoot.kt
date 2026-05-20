package com.example.aviscito.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.aviscito.features.home.HomeScreen
import com.example.aviscito.features.pilltracker.PillListScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                // All the screens
                polymorphic(NavKey::class) {
                    subclass(Route.HomeScreen::class, Route.HomeScreen.serializer())
                    subclass(Route.PillListScreen::class, Route.PillListScreen.serializer())
                }
            }
        }, Route.HomeScreen
    )
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when(key) {
                is Route.HomeScreen -> {
                    NavEntry(key) {
                        HomeScreen(onNavigateToPills = {
                            backStack.add(Route.PillListScreen)
                        })
                    }
                }
                is Route.PillListScreen -> {
                    NavEntry(key) {
                        PillListScreen()
                    }
                }
                else -> error("Unknown NavKey $key")
            }
        }
    )
}