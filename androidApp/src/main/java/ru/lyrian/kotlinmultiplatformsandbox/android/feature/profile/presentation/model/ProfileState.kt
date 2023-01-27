package ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.model

data class ProfileState(
    val userName: String,
    val encryptedText: String,
    val isSaveEnabled: Boolean
)
