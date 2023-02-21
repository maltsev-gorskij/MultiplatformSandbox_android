package ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.model.ProfileState
import ru.lyrian.kotlinmultiplatformsandbox.core.common.logger.SharedLogger
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.onException
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.onSuccess
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.Profile
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.ProfileInteractor

class ProfileViewModel(
    private val profileInteractor: ProfileInteractor,

    // Commented example of crashlytics non fatal error. Use in release build variant
    // private val firebaseIntegrationInteractor: FirebaseIntegrationInteractor
) : ViewModel() {

    private var savedProfile: Profile? = null
    private var profile: Profile? = null

    private val _state =
        MutableStateFlow(
            ProfileState(
                userName = "",
                encryptedText = "",
                isSaveEnabled = isSaveEnabled()
            )
        )
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            profileInteractor
                .getProfile()
                .onSuccess {
                    savedProfile = it
                    profile = it

                    _state.update {
                        ProfileState(
                            userName = profile?.userName.orEmpty(),
                            encryptedText = profile?.encryptedText.orEmpty(),
                            isSaveEnabled = isSaveEnabled()
                        )
                    }
                }
                .onException {
                    SharedLogger.logError(
                        message = "Cannot load profile",
                        throwable = it,
                        tag = this@ProfileViewModel.javaClass.simpleName
                    )
                }
        }


    }

    fun updateUserName(newUserName: String) {
        profile = profile?.copy(userName = newUserName)
        updateProfileState()
    }

    fun updateEncryptedText(newText: String) {
        profile = profile?.copy(encryptedText = newText)
        updateProfileState()
    }

    fun saveProfile() {
        // Commented example of crashlytics non fatal error. Use in release build variant
        // firebaseIntegrationInteractor.firebaseCrashlyticsTest()

        viewModelScope.launch {
            // Commented example of Firebase usage from KMM module
//            FirestoreClient().addUser("petya")
//            FirestoreClient().getFirstUser()

            profile?.let { profileInteractor.saveProfile(it) }
            profileInteractor
                .getProfile()
                .onSuccess {
                    savedProfile = it
                }
                .onException {
                    SharedLogger.logError(
                        message = "Cannot load profile",
                        throwable = it,
                        tag = this@ProfileViewModel.javaClass.simpleName
                    )
                }

            updateProfileState()
        }
    }

    private fun isSaveEnabled() = profile?.userName != savedProfile?.userName
            || profile?.encryptedText != savedProfile?.encryptedText

    private fun updateProfileState() {
        _state.update {
            ProfileState(
                userName = profile?.userName.orEmpty(),
                encryptedText = profile?.encryptedText.orEmpty(),
                isSaveEnabled = isSaveEnabled()
            )
        }
    }
}
