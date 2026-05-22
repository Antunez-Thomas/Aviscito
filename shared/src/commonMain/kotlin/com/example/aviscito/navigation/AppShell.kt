package com.example.aviscito.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppShell(
    modifier: Modifier = Modifier
) {
    val navViewModel = viewModel { NavViewModel() }
    val topLevelBackStack = navViewModel.bottomBarBackStack

    Scaffold(
        bottomBar = {
            NavigationBar {
                TOP_LEVEL_ROUTES.forEach { route ->
                    val isSelected = route == topLevelBackStack.topLevelKey
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { topLevelBackStack.addTopLevel(route) },
                        icon = {
                            Icon(
                                imageVector = route.icon,
                                contentDescription = route.label
                            )
                        },
                        label = { Text(route.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavigationRoot(
            modifier = modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        )
    }
}
