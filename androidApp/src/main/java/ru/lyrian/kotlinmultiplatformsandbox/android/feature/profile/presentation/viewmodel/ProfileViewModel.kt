package ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.model.ProfileState
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.Profile
import ru.lyrian.kotlinmultiplatformsandbox.feature.profile.domain.ProfileInteractor

class ProfileViewModel(
    private val profileInteractor: ProfileInteractor,
) : ViewModel() {

    private var savedProfile: Profile = profileInteractor.getProfile()
    private var profile: Profile = savedProfile

    private val _state =  MutableStateFlow(ProfileState(profile.userName, profile.encryptedText, isSaveEnabled()))
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    fun updateUserName(newUserName: String) {
        profile = profile.copy(userName = newUserName)
        updateProfileState()
    }

    fun updateEncryptedText(newText: String) {
        profile = profile.copy(encryptedText = newText)
        updateProfileState()
    }
    
    fun saveProfile() {
        profileInteractor.saveProfile(profile)
        savedProfile = profileInteractor.getProfile()
        updateProfileState()
    }

    private fun isSaveEnabled() = profile.userName != savedProfile.userName
            || profile.encryptedText != savedProfile.encryptedText

    private fun updateProfileState() {
        _state.update {
            ProfileState(
                userName = profile.userName,
                encryptedText = profile.encryptedText,
                isSaveEnabled = isSaveEnabled()
            )
        }
    }
}
