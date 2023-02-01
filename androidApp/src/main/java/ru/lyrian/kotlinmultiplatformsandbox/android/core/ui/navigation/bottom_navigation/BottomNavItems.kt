package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.bottom_navigation

import dev.icerock.moko.resources.StringResource
import ru.lyrian.kotlinmultiplatformsandbox.Resources
import ru.lyrian.kotlinmultiplatformsandbox.android.R
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.NavDestinations

sealed class BottomNavItems(val titleResource: StringResource, val icon: Int, val route: String) {
    object Launches : BottomNavItems(
        titleResource = Resources.strings.bottom_nav_launches,
        icon = R.drawable.ic_list,
        route = NavDestinations.HomeNavGraph.LAUNCHES
    )
    object Favorites : BottomNavItems(
        titleResource = Resources.strings.bottom_nav_favourites,
        icon = R.drawable.ic_favorites,
        route = NavDestinations.HomeNavGraph.FAVORITES
    )
    object Profile : BottomNavItems(
        titleResource = Resources.strings.bottom_nav_profile,
        icon = R.drawable.ic_profile,
        route = NavDestinations.HomeNavGraph.PROFILE
    )
}
