package ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.list.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import org.koin.androidx.compose.getViewModel
import ru.lyrian.kotlinmultiplatformsandbox.Resources
import ru.lyrian.kotlinmultiplatformsandbox.android.R
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens.LocalScaffoldPaddings
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.screens.LocalSnackBarState
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.list.presentation.model.LaunchesListState
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.list.presentation.model.LaunchesUiWrapper
import ru.lyrian.kotlinmultiplatformsandbox.android.feature.launches.list.presentation.viewmodel.LaunchesListViewModel
import ru.lyrian.kotlinmultiplatformsandbox.feature.launches.domain.RocketLaunch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LaunchesListScreen(
    onLaunchClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = getViewModel<LaunchesListViewModel>()
    val currentViewState by viewModel.viewState.collectAsState()
    val snackBarState = LocalSnackBarState.current


    LaunchedEffect(true) {
        viewModel.event.collect { event: LaunchesListEvent ->
            when (event) {
                is LaunchesListEvent.ShowToast -> {
                    snackBarState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(resource = Resources.strings.launches_header_text),
                        style = MaterialTheme.typography.h6
                    )
                }
            )
        }
    ) {
        Surface(
            modifier = modifier,
            color = Color.LightGray
        ) {
            LaunchesList(
                viewState = currentViewState,
                onRefresh = viewModel::refresh,
                onLoadNextPage = viewModel::loadNextPage,
                onLaunchClick = onLaunchClick
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LaunchesList(
    viewState: LaunchesListState,
    onRefresh: () -> Unit,
    onLoadNextPage: () -> Unit,
    onLaunchClick: (String) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewState.isLoading,
        onRefresh = onRefresh
    )

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LaunchesListContent(
            launches = viewState.launches,
            isLoadingNewPage = viewState.isLoadingNewPage,
            isError = viewState.isErrorLoading,
            isErrorLoadingNewPage = viewState.isErrorLoadingNewPage,
            errorMessage = viewState.errorMessage,
            onLoadNextPage = onLoadNextPage,
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
    launches: LaunchesUiWrapper,
    isLoadingNewPage: Boolean,
    isError: Boolean,
    isErrorLoadingNewPage: Boolean,
    errorMessage: String,
    onLoadNextPage: () -> Unit,
    onLaunchClick: (String) -> Unit
) {
    val bottomPadding = LocalScaffoldPaddings.current.calculateBottomPadding()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = 8.dp, bottom = bottomPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (isError) {
            item {
                Box(modifier = Modifier.fillParentMaxSize()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = errorMessage,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        } else {
            items(
                count = launches.size,
                key = { launches.peekItem(it).id }
            ) {
                LaunchesListItem(
                    rocketLaunch = launches[it],
                    onLaunchClick = { onLaunchClick(launches[it].id) }
                )
            }

            if (isErrorLoadingNewPage) {
                item(key = "error") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.body1
                        )
                        IconButton(onClick = onLoadNextPage) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                        }
                    }
                }
            } else if (isLoadingNewPage) {
                item(key = "loader") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
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
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp,
        onClick = onLaunchClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    text = stringResource(resource = Resources.strings.launch_card_year, rocketLaunch.launchYear ?: 0),
                    style = MaterialTheme.typography.caption
                )
                rocketLaunch.details?.let {
                    Text(
                        text = stringResource(resource = Resources.strings.launch_card_details, it),
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            rocketLaunch.youtubeId?.let {
                Icon(
                    modifier = Modifier.align(Alignment.TopEnd),
                    painter = painterResource(id = R.drawable.ic_media),
                    contentDescription = "Media icon",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}
