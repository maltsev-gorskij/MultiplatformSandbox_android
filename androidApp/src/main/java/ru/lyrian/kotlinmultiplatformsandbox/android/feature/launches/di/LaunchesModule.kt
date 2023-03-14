package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.details.presentation.viewmodel.LaunchDetailsViewModel
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.list.presentation.viewmodel.LaunchesListViewModel

val launchesModule = module {
    viewModelOf(::LaunchDetailsViewModel)
    viewModelOf(::LaunchesListViewModel)
}