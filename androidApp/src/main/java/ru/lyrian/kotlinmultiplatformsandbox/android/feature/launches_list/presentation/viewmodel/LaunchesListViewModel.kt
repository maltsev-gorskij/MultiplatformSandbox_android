package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.mvvm.ResourceState
import dev.icerock.moko.mvvm.livedata.asFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lyrian.kotlinmultiplatformsandbox.android.core.constants.PaginationConstants
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.model.LaunchesListState
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.model.LaunchesUiWrapper
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.ui.LaunchesListEvent
import ru.lyrian.kotlinmultiplatformsandbox.core.data.data_source.api.interceptors_applicator.ApiError
import ru.lyrian.kotlinmultiplatformsandbox.core.logger.SharedLogger
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.LaunchesInteractor
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.PaginationState
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

class LaunchesListViewModel constructor(
    private val launchesInteractor: LaunchesInteractor
) : ViewModel() {
    private val _viewState = MutableStateFlow(LaunchesListState())
    val viewState = _viewState.asStateFlow()

    private val _event = Channel<LaunchesListEvent>()
    val event = _event.receiveAsFlow()

    private var paginationState: PaginationState? = null

    init {
        viewModelScope.launch {
            paginationState = launchesInteractor.initializePagination()

            launch {
                paginationState
                    ?.resourceState
                    ?.asFlow()
                    ?.collect {
                        handlePaginationState(it)
                    }
            }

            launch {
                paginationState
                    ?.refreshLoadingState
                    ?.asFlow()
                    ?.collect {
                        handleRefreshLoadingState(it)
                    }
            }

            launch {
                paginationState
                    ?.nextPageLoadingState
                    ?.asFlow()
                    ?.collect {
                        handleNextPageLoadState(it)
                    }
            }
        }
    }

    private fun handlePaginationState(resourceState: ResourceState<List<RocketLaunch>, Throwable>) {
        when (resourceState) {
            is ResourceState.Loading -> {
                _viewState.update {
                    it.copy(
                        isLoading = true,
                        isErrorLoading = false,
                        errorMessage = "",
                        isLoadingNewPage = false,
                        isErrorLoadingNewPage = false
                    )
                }
            }
            is ResourceState.Empty -> {
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        isErrorLoading = false,
                        errorMessage = "",
                        isLoadingNewPage = false,
                        isErrorLoadingNewPage = false
                    )
                }
            }
            is ResourceState.Success -> {
                _viewState.update {
                    it.copy(
                        launches = LaunchesUiWrapper(
                            launches = resourceState.dataValue() ?: emptyList(),
                            pagingThreshold = PaginationConstants.NEW_PAGE_LOAD_THRESHOLD
                        ) { loadNextPage() },
                        isLoading = false,
                        isErrorLoading = false,
                        errorMessage = "",
                        isLoadingNewPage = false,
                        isErrorLoadingNewPage = false
                    )
                }
            }
            is ResourceState.Failed -> {
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        isErrorLoading = true,
                        errorMessage = getErrorText(resourceState.errorValue()),
                        isLoadingNewPage = false,
                    )
                }

                handleError(resourceState.errorValue())
            }
        }
    }

    private fun handleNextPageLoadState(resourceState: ResourceState<List<RocketLaunch>, Throwable>) {
        when (resourceState) {
            is ResourceState.Empty -> {
                _viewState.update {
                    it.copy(
                        isLoadingNewPage = false
                    )
                }
            }
            is ResourceState.Loading -> {
                _viewState.update {
                    it.copy(
                        isLoadingNewPage = true
                    )
                }
            }
            is ResourceState.Success -> {
                _viewState.update {
                    it.copy(
                        launches = it.launches.copy(isLoadingPage = false),
                        isLoadingNewPage = false
                    )
                }
            }
            is ResourceState.Failed -> {
                _viewState.update {
                    it.copy(
                        isLoadingNewPage = false,
                        isErrorLoadingNewPage = true,
                        errorMessage = getErrorText(resourceState.errorValue())
                    )
                }
                SharedLogger.logError(
                    message = "Failed loading next page",
                    throwable = resourceState.errorValue(),
                    tag = this@LaunchesListViewModel.javaClass.simpleName
                )
            }
        }
    }

    private fun handleRefreshLoadingState(resourceState: ResourceState<List<RocketLaunch>, Throwable>) {
        when (resourceState) {
            is ResourceState.Empty -> {
                _viewState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
            is ResourceState.Loading -> {
                _viewState.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }
            is ResourceState.Success -> {
                _viewState.update {
                    it.copy(
                        launches = it.launches.copy(isLoadingPage = false),
                        isLoading = false
                    )
                }
            }
            is ResourceState.Failed -> {
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        isErrorLoading = true,
                        errorMessage = getErrorText(resourceState.errorValue())
                    )
                }

                handleError(resourceState.errorValue())
            }
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            _viewState.update {
                it.copy(
                    launches = it.launches.copy(isLoadingPage = true),
                )
            }
            launchesInteractor.loadNextPage()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            launchesInteractor.refreshPagination()
        }
    }

    private fun handleError(throwable: Throwable?) {
        SharedLogger.logError(
            message = "Failed loading launches",
            throwable = throwable,
            tag = this@LaunchesListViewModel.javaClass.simpleName
        )

        val toastMessage = getErrorText(throwable)

        viewModelScope.launch {
            _event.send(LaunchesListEvent.ShowToast(toastMessage))
        }
    }

    private fun getErrorText(throwable: Throwable?): String {
        return when (throwable) {
            is ApiError.BadRequest -> "Bad network request"
            is ApiError.NetworkError -> "Network Error"
            is ApiError.ServerError -> "Remote Server Error"
            is ApiError.Unauthorized -> "User Unauthorized"
            else -> "Undefined error"
        }
    }
}
