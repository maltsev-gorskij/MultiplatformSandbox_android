package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.bottom_navigation

import dev.icerock.moko.resources.StringResource
import ru.lyrian.kotlinmultiplatformsandbox.Resources
import ru.lyrian.kotlinmultiplatformsandbox.android.R
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations.Destinations.RootGraph.HomeGraph

sealed class BottomNavItems(val titleResource: StringResource, val icon: Int, val route: String) {
    object Launches : BottomNavItems(
        titleResource = Resources.strings.bottom_nav_launches,
        icon = R.drawable.ic_list,
        route = HomeGraph.LAUNCHES
    )
    object Media : BottomNavItems(
        titleResource = Resources.strings.bottom_nav_videos,
        icon = R.drawable.ic_media,
        route = HomeGraph.VIDEOS
    )
    object Profile : BottomNavItems(
        titleResource = Resources.strings.bottom_nav_profile,
        icon = R.drawable.ic_profile,
        route = HomeGraph.PROFILE
    )
}
