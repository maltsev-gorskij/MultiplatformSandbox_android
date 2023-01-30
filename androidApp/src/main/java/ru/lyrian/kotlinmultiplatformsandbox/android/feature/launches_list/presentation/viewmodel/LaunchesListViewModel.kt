package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.model.LaunchesListState
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.ui.LaunchesListEvent
import ru.lyrian.kotlinmultiplatformsandbox.core.logger.SharedLogger
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.LaunchesInteractor

class LaunchesListViewModel constructor(
    private val launchesInteractor: LaunchesInteractor
) : ViewModel() {
    private val _viewState = MutableStateFlow(LaunchesListState())
    val viewState = _viewState.asStateFlow()

    private val _event = Channel<LaunchesListEvent>()
    val event = _event.receiveAsFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            SharedLogger.logError(
                message = "Failed loading launches",
                throwable = throwable,
                tag = this.javaClass.simpleName

            )
            _event.send(LaunchesListEvent.ShowToast("Update failed" + throwable.localizedMessage?.let { ": $it" }))

            _viewState.update {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }

    init {
        refresh()
    }

    fun refresh(forceReload: Boolean = false) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _viewState.update {
                it.copy(
                    isLoading = true
                )
            }

            val launches = launchesInteractor.getAllLaunches(forceReload)

            _viewState.update {
                it.copy(
                    isError = false,
                    isLoading = false,
                    launches = launches
                )
            }

            if (forceReload) {
                _event.send(LaunchesListEvent.ShowToast("Reload completed"))
            }
        }
    }
}
