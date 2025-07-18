@file:OptIn(ExperimentalMaterial3Api::class)

package com.jefisu.trackizer.feature.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.AnimatedTabs
import com.jefisu.trackizer.core.designsystem.components.Tab
import com.jefisu.trackizer.core.designsystem.components.TrackizerTopBar
import com.jefisu.trackizer.core.designsystem.components.TrackizerTopBarDefaults
import com.jefisu.trackizer.core.designsystem.util.previewSubscriptions
import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.ui.filterUpcomingBills
import com.jefisu.trackizer.feature.home.presentation.components.SubscriptionDashboard
import com.jefisu.trackizer.feature.home.presentation.components.SubscriptionList
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import org.koin.compose.viewmodel.koinViewModel
import trackizer.core.designsystem.generated.resources.Res as DesignSystemRes
import trackizer.core.designsystem.generated.resources.subscriptions
import trackizer.feature.home.generated.resources.Res
import trackizer.feature.home.generated.resources.upcoming_bills_tab
import trackizer.feature.home.generated.resources.you_don_t_have_any
import trackizer.feature.home.generated.resources.your_subscriptions_tab

@Composable
fun HomeScreenRoot(viewModel: HomeViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
    )
}

@Composable
fun HomeScreen(
    state: HomeState,
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit = {},
    onSeeBudgetClick: () -> Unit = {},
) {
    Scaffold(
        containerColor = Color.Transparent,
        modifier = modifier,
        topBar = {
            HomeTopBar(onSettingsClick = onSettingsClick)
        },
    ) { paddingValues ->
        HomeContent(
            state = state,
            onSeeBudgetClick = onSeeBudgetClick,
        )
    }
}

@Composable
private fun HomeTopBar(onSettingsClick: () -> Unit, modifier: Modifier = Modifier) {
    TrackizerTopBar(
        title = null,
        modifier = modifier,
        actions = {
            TrackizerTopBarDefaults.SettingsIcon(
                onClick = onSettingsClick,
            )
        },
    )
}

@Composable
private fun HomeContent(
    state: HomeState,
    onSeeBudgetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        SubscriptionDashboard(
            subscriptions = state.subscriptions,
            monthlyBudget = state.monthlyBudget.toDouble(),
            onSeeBudgetClick = onSeeBudgetClick,
        )
        Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
        SubscriptionTabs(
            subscriptions = state.subscriptions,
        )
    }
}

@Composable
fun SubscriptionTabs(subscriptions: List<Subscription>, modifier: Modifier = Modifier) {
    val tabs = remember {
        listOf(
            TabType.YOUR_SUBSCRIPTIONS,
            TabType.UPCOMING_BILLS,
        )
    }

    AnimatedTabs(
        tabs = tabs.map { tabType ->
            createSubscriptionTab(
                tabType = tabType,
                subscriptions = subscriptions,
            )
        },
        modifier = modifier.padding(horizontal = TrackizerTheme.spacing.extraMedium),
    )
}

@Composable
private fun createSubscriptionTab(tabType: TabType, subscriptions: List<Subscription>): Tab {
    val contentPadding = PaddingValues(
        start = TrackizerTheme.spacing.extraMedium,
        end = TrackizerTheme.spacing.extraMedium,
    )

    return when (tabType) {
        TabType.YOUR_SUBSCRIPTIONS -> Tab(
            title = stringResource(Res.string.your_subscriptions_tab),
            content = {
                YourSubscriptionsTabContent(
                    subscriptions = subscriptions,
                    contentPadding = contentPadding,
                )
            },
        )
        TabType.UPCOMING_BILLS -> Tab(
            title = stringResource(Res.string.upcoming_bills_tab),
            content = {
                UpcomingBillsTabContent(
                    subscriptions = subscriptions,
                    contentPadding = contentPadding,
                )
            },
        )
    }
}

@Composable
private fun YourSubscriptionsTabContent(
    subscriptions: List<Subscription>,
    contentPadding: PaddingValues,
) {
    SubscriptionList(
        subscriptions = subscriptions,
        contentPadding = contentPadding,
        messageEmptyList = stringResource(
            resource = Res.string.you_don_t_have_any,
            stringResource(DesignSystemRes.string.subscriptions).lowercase(),
        ),
    )
}

@Composable
private fun UpcomingBillsTabContent(
    subscriptions: List<Subscription>,
    contentPadding: PaddingValues,
) {
    val upcomingBills = remember(subscriptions) {
        subscriptions.filterUpcomingBills()
    }

    SubscriptionList(
        subscriptions = upcomingBills,
        contentPadding = contentPadding,
        messageEmptyList = stringResource(
            resource = Res.string.you_don_t_have_any,
            stringResource(Res.string.upcoming_bills_tab).lowercase(),
        ),
        upcomingBill = true,
    )
}

private enum class TabType {
    YOUR_SUBSCRIPTIONS,
    UPCOMING_BILLS,
}

@Preview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewParameter::class) state: HomeState,
) {
    TrackizerTheme {
        HomeScreen(
            state = state,
        )
    }
}

private class HomeScreenPreviewParameter : PreviewParameterProvider<HomeState> {
    override val values = sequenceOf(
        HomeState(),
        HomeState(
            subscriptions = previewSubscriptions,
            monthlyBudget = 50f,
        ),
    )
}
