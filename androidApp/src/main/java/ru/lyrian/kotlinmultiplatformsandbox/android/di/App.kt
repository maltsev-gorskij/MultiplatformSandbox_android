package ru.lyrian.kotlinmultiplatformsandbox.android.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import ru.lyrian.kotlinmultiplatformsandbox.android.BuildConfig
import ru.lyrian.kotlinmultiplatformsandbox.core.di.KoinInitializer

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinInitializer {
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(androidModules())
        }.initializeKoin()
    }
}
