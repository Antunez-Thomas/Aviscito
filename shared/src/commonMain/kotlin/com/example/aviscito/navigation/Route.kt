package com.example.aviscito.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import compose.icons.FeatherIcons
import compose.icons.feathericons.Clock
import compose.icons.feathericons.Heart
import compose.icons.feathericons.Home
import compose.icons.feathericons.User
import kotlinx.serialization.Serializable

interface TopLevelRoute : NavKey {
    val icon: ImageVector
    val label: String
}

@Serializable
data object HomeScreen : TopLevelRoute {
    override val icon = FeatherIcons.Home
    override val label = "Home"
}

@Serializable
data object PillListScreen : TopLevelRoute {
    override val icon = FeatherIcons.Heart
    override val label = "Pills"
}

@Serializable
data object History : TopLevelRoute {
    override val icon = FeatherIcons.Clock
    override val label = "History"
}

@Serializable
data object Profile : TopLevelRoute {
    override val icon = FeatherIcons.User
    override val label = "Profile"
}

@Serializable
data object Tabs : NavKey

val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(HomeScreen, PillListScreen, History, Profile)
