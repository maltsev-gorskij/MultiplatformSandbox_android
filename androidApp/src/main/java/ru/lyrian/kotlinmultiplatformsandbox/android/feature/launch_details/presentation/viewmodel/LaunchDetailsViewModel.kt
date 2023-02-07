package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.model.LaunchDetailsEvent
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launch_details.presentation.model.LaunchDetailsState

//    private val launchesInteractor: LaunchesInteractor,
//    savedStateHandle: SavedStateHandle
class LaunchDetailsViewModel: ViewModel() {

//    private val launchDetailsArgs = LaunchDetailsArgs(savedStateHandle)
//    private var launch: RocketLaunch? = null

    private val _state = MutableStateFlow(LaunchDetailsState())
    val state: StateFlow<LaunchDetailsState> = _state.asStateFlow()

    private val _eventChannel: Channel<LaunchDetailsEvent> = Channel()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
//        loadLaunch(launchDetailsArgs.launchId)
    }

//    fun refreshLaunchDetails() = loadLaunch(launchDetailsArgs.launchId)

   /* private fun loadLaunch(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isError = false
                    )
                }
                launchesInteractor.getLaunchById(id)
            }.onSuccess { rocketLaunch ->
                launch = rocketLaunch
                _state.update {
                    it.copy(
                        launch = launch,
                        isLoading = false
                    )
                }
            }.onFailure { throwable ->
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
    }*/
}
