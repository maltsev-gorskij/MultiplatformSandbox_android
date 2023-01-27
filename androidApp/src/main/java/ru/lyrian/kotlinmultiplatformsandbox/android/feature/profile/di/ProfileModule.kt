package ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.viewmodel.ProfileViewModel

val profileModule = module {
    viewModelOf(::ProfileViewModel)
}
