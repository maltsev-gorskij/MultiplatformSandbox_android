package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.list.presentation.model

import ru.lyrian.kotlinmultiplatformsandbox.android.core.constants.PaginationConstants

data class LaunchesListState(
    val launches: LaunchesUiWrapper = LaunchesUiWrapper(emptyList(), PaginationConstants.NEW_PAGE_LOAD_THRESHOLD) { },
    val isLoading: Boolean = false,
    val isErrorLoading: Boolean = false,
    val errorMessage: String = "",
    val isLoadingNewPage: Boolean = false,
    val isErrorLoadingNewPage: Boolean = false,
)
