package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import org.koin.androidx.compose.getViewModel
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens.LocalSnackBarState
import ru.lyrian.kotlinmultiplatformsandbox.Resources
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.model.LaunchesListState
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches_list.presentation.viewmodel.LaunchesListViewModel
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

@Composable
fun LaunchesListScreen(
    onLaunchClick: (String) -> Unit
) {
    val viewModel = getViewModel<LaunchesListViewModel>()
    val currentViewState by viewModel.viewState.collectAsState()
    val snackBarState = LocalSnackBarState.current

    LaunchedEffect(true) {
        viewModel.event.collect { event: LaunchesListEvent ->
            when (event) {
                is LaunchesListEvent.ShowToast -> {
                    snackBarState.showSnackbar(message = event.message)
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.LightGray
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LaunchesHeader()
            LaunchesList(
                viewState = currentViewState,
                onRefresh = { viewModel.refresh(true) },
                onLaunchClick = onLaunchClick
            )
        }
    }
}

@Composable
private fun LaunchesHeader() {
    Box(
        Modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.primary
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp),
                text = stringResource(resource = Resources.strings.launches_header_text),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LaunchesList(
    viewState: LaunchesListState,
    onRefresh: () -> Unit,
    onLaunchClick: (String) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewState.isLoading,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LaunchesListContent(
            viewState = viewState,
            onLaunchClick = onLaunchClick
        )
        PullRefreshIndicator(
            refreshing = viewState.isLoading,
            state = pullRefreshState,
            scale = true,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun LaunchesListContent(
    viewState: LaunchesListState,
    onLaunchClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (viewState.isError) {
            item() {
                Box(modifier = Modifier.fillParentMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = viewState.errorMessage,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        } else {
            items(items = viewState.launches, key = { it.id }) {
                LaunchesListItem(
                    rocketLaunch = it,
                    onLaunchClick = { onLaunchClick(it.id) }
                )
            }
        }
    }
}

@Composable
private fun LaunchesListItem(
    rocketLaunch: RocketLaunch,
    onLaunchClick: () -> Unit
) {
    val successTextResource = if (rocketLaunch.launchSuccess == true) {
        Resources.strings.launch_card_successful
    } else {
        Resources.strings.launch_card_unsuccessful
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onLaunchClick),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = stringResource(resource = Resources.strings.launch_card_name, rocketLaunch.missionName),
                style = MaterialTheme.typography.caption
            )
            Text(
                text = stringResource(resource = successTextResource),
                style = MaterialTheme.typography.caption.copy(
                    color = if (rocketLaunch.launchSuccess == false) {
                        MaterialTheme.colors.error
                    } else {
                        LocalTextStyle.current.color
                    }
                )
            )
            Text(
                text = stringResource(resource = Resources.strings.launch_card_year, rocketLaunch.launchYear),
                style = MaterialTheme.typography.caption
            )
            rocketLaunch.details?.let {
                Text(
                    text = stringResource(resource = Resources.strings.launch_card_details, it),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}
