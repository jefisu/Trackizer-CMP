package com.jefisu.trackizer.navigation.graph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.feature.home.presentation.HomeScreenRoot
import com.jefisu.trackizer.navigation.AnimationTarget
import com.jefisu.trackizer.navigation.AnimationType
import com.jefisu.trackizer.navigation.Destination
import com.jefisu.trackizer.navigation.animatedScreen
import com.jefisu.trackizer.navigation.sharedViewModel

fun NavGraphBuilder.loggedGraph() {
    navigation<Destination.LoggedGraph>(
        startDestination = Destination.HomeScreen,
    ) {
        animatedScreen<Destination.HomeScreen> {
            HomeScreenRoot(
                viewModel = sharedViewModel(),
                onNavigateToSettings = {},
                onNavigateToSeeBudget = {},
            )
        }
        animatedScreen<Destination.SpendingBudgetsScreen> {
            DraftScreen("SpendingBudgetsScreen")
        }
        animatedScreen<Destination.AddSubscriptionScreen> {
            DraftScreen("AddSubscriptionScreen")
        }
        animatedScreen<Destination.CalendarScreen> {
            DraftScreen("CalendarScreen")
        }
        animatedScreen<Destination.CreditCardsScreen> {
            DraftScreen("CreditCardsScreen")
        }
    }
}

val loggedNavAnimConfig: List<Pair<Destination, List<AnimationTarget>>> = buildList {
    val destinations = listOf(
        Destination.HomeScreen,
        Destination.SpendingBudgetsScreen,
        Destination.CalendarScreen,
        Destination.CreditCardsScreen,
    )
    destinations.forEach { destination ->
        add(
            destination to listOf(
                AnimationTarget(Destination.AddSubscriptionScreen, AnimationType.BOTTOM_TO_TOP),
            ),
        )
    }
}

@Composable
fun DraftScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, style = TrackizerTheme.typography.headline5)
    }
}