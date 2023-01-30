package ru.lyrian.kotlinmultiplatformsandbox.android.di

import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import ru.lyrian.kotlinmultiplatformsandbox.core.di.KoinInitializer
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.AppInitializer

class App : Application() {
    private val appInitializer: AppInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        KoinInitializer {
            androidContext(this@App)
            modules(androidModules())
        }.initializeKoin()

        appInitializer.init()
    }
}
