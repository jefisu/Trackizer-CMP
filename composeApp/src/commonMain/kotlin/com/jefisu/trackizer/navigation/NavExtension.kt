package com.jefisu.trackizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jefisu.trackizer.core.ui.LocalAnimatedContentScope
import org.koin.compose.currentKoinScope
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.scope.Scope

fun NavBackStackEntry.isCurrentDestination(destination: Destination) = this.destination.hasRoute(
    destination::class,
)

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
    scope: Scope = currentKoinScope(),
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry,
        scope = scope,
    )
}

inline fun <reified T : Destination> NavController.navigateSingleTopTo(destination: Destination) {
    navigate(destination) {
        popUpTo<T>()
        launchSingleTop = true
    }
}

inline fun <reified T : Destination> NavGraphBuilder.animatedScreen(
    noinline content: @Composable NavBackStackEntry.() -> Unit,
) {
    composable<T> { backStack ->
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            content(backStack)
        }
    }
}
