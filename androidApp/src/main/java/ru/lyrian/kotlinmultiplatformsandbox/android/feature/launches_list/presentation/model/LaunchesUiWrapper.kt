package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.model

import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

/**
 * Wrapped paginated launches list.
 * Provides next page load call if passed pagingThreshold reached and several helper functions
 *
 * @param launches current launches list to pass in Lazy UI object.
 * @param pagingThreshold if currently requested list index passed beyond with value from the end,
 * [onNextPageRequest] callback will be invoked. [isLoadingPage] value must set to true during loading event
 * to prevent repeated [onNextPageRequest] if previous loading is not completed yet.
 * @param isLoadingPage denotes current next page loading state, triggered from inside this wrapper.
 * @param onNextPageRequest lambda describe the way to call pagination library for fetching new page
* */
data class LaunchesUiWrapper(
    val launches: List<RocketLaunch>,
    val pagingThreshold: Int,
    val isLoadingPage: Boolean = false,
    val onNextPageRequest: () -> Unit
) {
    /**
     * Return size of nested launches list.
     * */
    val size
        get() = launches.size

    /**
     * Return true is nested launches list is empty.
     * */
    fun isEmpty() = launches.isEmpty()

    /**
     * Peek item without triggering paging threshold check.
     * */
    fun peekItem(requestedIndex: Int): RocketLaunch = launches[requestedIndex]

    /**
     * Peek item with triggering paging threshold check. If it is reached, calling onNextPageRequest lambda block
     * */
    operator fun get(requestedIndex: Int): RocketLaunch {
        val launchesMaxIndex = launches.size - 1
        val pagingThresholdDelta = launchesMaxIndex - requestedIndex
        if (pagingThresholdDelta < pagingThreshold && !isLoadingPage) {
            onNextPageRequest()
        }

        return launches[requestedIndex]
    }
}