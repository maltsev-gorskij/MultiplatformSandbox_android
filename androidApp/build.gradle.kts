import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariantOutput
import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "ru.lyrian.kotlinmultiplatformsandbox.android"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "ru.lyrian.kotlinmultiplatformsandbox.android"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        versionCode = rootProject.extra["versionCode"] as Int
        versionName = rootProject.extra["versionName"] as String
        resValue("string", "app_name", rootProject.extra["appName"] as String)

        signingConfig = signingConfigs.getByName("debug")
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    applicationVariants.all(ApplicationVariantAction())
}

dependencies {
    // Importing shared kmm logic from mavenLocal repo
    implementation(libs.kotlinmultiplatformsandbox.shared)

    // All compose ui related dependencies bundle
    implementation(libs.bundles.compose)

    // Coroutines
    implementation(libs.coroutines.android)

    // Koin DI for Android and Compose
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Multiplatform resources
    implementation(libs.resources.compose)

    // Unit testing
    testImplementation(libs.koin.test)
    testImplementation(libs.koin.test.junit)
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    testImplementation(libs.coroutines.test)
}

class ApplicationVariantAction : Action<ApplicationVariant> {
    override fun execute(variant: ApplicationVariant) {
        val fileName = createFileName(variant)
        variant.outputs.all(VariantOutputAction(fileName))
    }

    private fun createFileName(variant: ApplicationVariant): String {
        return rootProject.name +
                "_${variant.name}" +
                "_${rootProject.extra["versionName"] as String}" +
                ".apk"
    }

    class VariantOutputAction(
        private val fileName: String
    ) : Action<BaseVariantOutput> {
        override fun execute(output: BaseVariantOutput) {
            if (output is BaseVariantOutputImpl) {
                output.outputFileName = fileName
            }
        }
    }
}