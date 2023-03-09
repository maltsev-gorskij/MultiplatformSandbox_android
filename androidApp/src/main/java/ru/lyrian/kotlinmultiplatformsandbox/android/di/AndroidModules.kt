package ru.lyrian.kotlinmultiplatformsandbox.android.di

import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.di.launchesModule
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.di.profileModule
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.videos.di.videosModule

fun androidModules() = listOf(
    launchesModule,
    profileModule,
    videosModule
)
