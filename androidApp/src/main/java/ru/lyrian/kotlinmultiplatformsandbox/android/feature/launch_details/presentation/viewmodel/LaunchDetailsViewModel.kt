package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.model.LaunchDetailsArgs
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.model.LaunchDetailsEvent
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.model.LaunchDetailsState
import ru.lyrian.kotlinmultiplatformsandbox.core.common.logger.SharedLogger
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.onException
import ru.lyrian.kotlinmultiplatformsandbox.core.domain.onSuccess
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.LaunchesInteractor
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

class LaunchDetailsViewModel constructor(
    private val launchesInteractor: LaunchesInteractor,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val launchDetailsArgs = LaunchDetailsArgs(savedStateHandle)
    private var launch: RocketLaunch? = null

    private val _state = MutableStateFlow(LaunchDetailsState())
    val state: StateFlow<LaunchDetailsState> = _state.asStateFlow()

    private val _eventChannel: Channel<LaunchDetailsEvent> = Channel()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        loadLaunch(launchDetailsArgs.launchId)
    }

    fun refreshLaunchDetails() = loadLaunch(launchDetailsArgs.launchId)

    private fun loadLaunch(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }

            launchesInteractor
                .getLaunchById(id)
                .onSuccess { rocketLaunch: RocketLaunch ->
                    launch = rocketLaunch
                    _state.update {
                        it.copy(
                            launch = launch,
                            isLoading = false
                        )
                    }
                }
                .onException { throwable: Throwable ->
                    SharedLogger.logError(
                        message = "Failed load launch details",
                        throwable = throwable,
                        tag = this@LaunchDetailsViewModel.javaClass.simpleName
                    )
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = true
                        )
                    }

                    _eventChannel.send(LaunchDetailsEvent.ShowErrorMessage("Error: ${throwable.localizedMessage}"))
                }
        }
    }
}
