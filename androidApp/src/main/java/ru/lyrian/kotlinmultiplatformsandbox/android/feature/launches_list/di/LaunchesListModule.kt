package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.viewmodel.LaunchesListViewModel

val launchesListModule = module {
    viewModelOf(::LaunchesListViewModel)
}
