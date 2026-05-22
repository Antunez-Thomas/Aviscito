package com.example.aviscito.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.aviscito.features.history.HistoryScreen
import com.example.aviscito.features.home.HomeScreen
import com.example.aviscito.features.pilltracker.PillListScreen
import com.example.aviscito.features.profile.ProfileScreen

class NavViewModel : ViewModel() {
    var bottomBarBackStack by mutableStateOf(BottomBarBackStack<TopLevelRoute>(HomeScreen))
        private set
}

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
) {
    val navViewModel = viewModel { NavViewModel() }
    val topLevelBackStack = navViewModel.bottomBarBackStack

    NavDisplay(
        modifier = modifier,
        backStack = topLevelBackStack.backStack,
        onBack = { topLevelBackStack.removeLast() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider({ key -> NavEntry(key) { Text("Unknown Screen") } }) {
            entry<HomeScreen> {
                HomeScreen(onNavigateToPills = {
                    topLevelBackStack.add(PillListScreen)
                })
            }
            entry<PillListScreen> {
                PillListScreen(
                    onNavigateBack = { topLevelBackStack.removeLast() }
                )
            }
            entry<History> {
                HistoryScreen()
            }
            entry<Profile> {
                ProfileScreen()
            }
        }
    )
}
