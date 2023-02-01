package ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import org.koin.androidx.compose.getViewModel
import ru.lyrian.kotlinmultiplatformsandbox.Resources
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.profile.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = getViewModel<ProfileViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(
        modifier = modifier
    ) {
        ProfileContent(
            userName = state.userName,
            text = state.encryptedText,
            isSaveEnabled = state.isSaveEnabled,
            onUserNameChange = viewModel::updateUserName,
            onTextChange = viewModel::updateEncryptedText,
            onSaveClick = viewModel::saveProfile,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        )
    }
}

@Composable
private fun ProfileContent(
    userName: String,
    text: String,
    isSaveEnabled: Boolean,
    onUserNameChange: (String) -> Unit,
    onTextChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = userName,
                onValueChange = onUserNameChange,
                label = { Text(text = stringResource(resource = Resources.strings.profile_username_title)) },
                placeholder = { Text(text = stringResource(resource = Resources.strings.profile_username_hint)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = text,
                onValueChange = onTextChange,
                label = { Text(text = stringResource(resource = Resources.strings.profile_text_title)) },
                placeholder = { Text(text = stringResource(resource = Resources.strings.profile_text_hint)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = onSaveClick,
            enabled = isSaveEnabled,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(resource = Resources.strings.common_save),
                style = MaterialTheme.typography.button,
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }
    }
}
