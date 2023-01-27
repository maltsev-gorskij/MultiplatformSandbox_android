package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.viewmodel.LaunchDetailsViewModel

val launchDetailsModule = module {
    viewModelOf(::LaunchDetailsViewModel)
}
