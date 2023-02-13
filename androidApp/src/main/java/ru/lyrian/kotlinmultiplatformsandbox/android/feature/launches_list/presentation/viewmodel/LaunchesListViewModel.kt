package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination.PaginationProvider
import ru.lyrian.kotlinmultiplatformsandbox.core.data.pagination.PaginationState
import ru.lyrian.kotlinmultiplatformsandbox.core.logger.SharedLogger
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.LaunchesInteractor
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

class LaunchesListViewModel constructor(
    launchesInteractor: LaunchesInteractor,
) : ViewModel() {
    private val _viewState = MutableStateFlow(LaunchesListState())
    val viewState = _viewState.asStateFlow()

    private val _event = Channel<LaunchesListEvent>()
    val event = _event.receiveAsFlow()

    private var paginationProvider: PaginationProvider<RocketLaunch> = launchesInteractor.getPaginationProvider()

    init {
        viewModelScope.launch {
            paginationProvider
                .paginationState
                .collect { paginationState: PaginationState<List<RocketLaunch>, Throwable> ->
                    when (paginationState) {
                        is PaginationState.Failed -> {
                            _viewState.update {
                                it.copy(
                                    isLoading = false,
                                    isErrorLoading = paginationState.isFirstPage,
                                    errorMessage = paginationState.exception.localizedMessage.orEmpty(),
                                    isLoadingNewPage = false,
                                    isErrorLoadingNewPage = !paginationState.isFirstPage
                                )
                            }

                            SharedLogger.logError(
                                message = "Pagination error occurred",
                                throwable = paginationState.exception,
                                tag = this@LaunchesListViewModel.javaClass.simpleName
                            )
                        }
                        is PaginationState.Loading -> {
                            _viewState.update {
                                it.copy(
                                    isLoading = paginationState.isFirstPage,
                                    isErrorLoading = false,
                                    errorMessage = "",
                                    isLoadingNewPage = !paginationState.isFirstPage,
                                    isErrorLoadingNewPage = false
                                )
                            }
                        }
                        is PaginationState.Success -> {
                            _viewState.update {
                                it.copy(
                                    launches = LaunchesUiWrapper(
                                        launches = paginationState.data,
                                        pagingThreshold = PaginationConstants.NEW_PAGE_LOAD_THRESHOLD,
                                        isLoadingPage = false
                                    ) { loadNextPage() },
                                    isLoading = false,
                                    isErrorLoading = false,
                                    errorMessage = "",
                                    isLoadingNewPage = false,
                                    isErrorLoadingNewPage = false
                                )
                            }

                            if(paginationState.isRefreshed) _event.send(LaunchesListEvent.ShowToast("Success"))
                        }
                    }
                }
        }
    }

    fun loadNextPage() {
        _viewState.update {
            it.copy(
                launches = it.launches.copy(isLoadingPage = true),
            )
        }
        paginationProvider.loadNextPage()
    }

    fun refresh() {
        paginationProvider.refreshPagination()
    }
}
