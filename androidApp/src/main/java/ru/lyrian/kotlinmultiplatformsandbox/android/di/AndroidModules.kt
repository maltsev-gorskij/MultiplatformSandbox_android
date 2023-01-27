package ru.lyrian.kotlinmultiplatformsandbox.android.di

import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.di.launchDetailsModule
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.di.launchesListModule

fun androidModules() = listOf(
    launchesListModule,
    launchDetailsModule
)
