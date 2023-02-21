package ru.lyrian.kotlinmultiplatformsandbox.android.di

import android.app.Application
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import ru.lyrian.kotlinmultiplatformsandbox.android.BuildConfig
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.AppInitializer
import ru.lyrian.kotlinmultiplatformsandbox.core.initializers.KoinInitializer

class App : Application() {
    private val appInitializer: AppInitializer by inject()

    override fun onCreate() {
        super.onCreate()

        // Android and KMM DI graph initialization
        KoinInitializer {
            androidContext(this@App)
            modules(androidModules())
        }.initializeKoin()

        // Initialization of KMM module
        appInitializer.init()

        // Crashlytics toggle
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(BuildConfig.IS_CRASHLYTICS_ENABLED)
    }
}
