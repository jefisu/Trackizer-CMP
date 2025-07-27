@file:OptIn(KoinExperimentalAPI::class)

package com.jefisu.trackizer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.setSingletonImageLoaderFactory
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.BottomNavItem
import com.jefisu.trackizer.core.designsystem.components.FlashMessageDialog
import com.jefisu.trackizer.core.designsystem.components.TrackizerBottomNavBar
import com.jefisu.trackizer.core.domain.repository.UserRepository
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.ObserveAsEvents
import com.jefisu.trackizer.core.util.closeKoinScope
import com.jefisu.trackizer.di.AppModule
import com.jefisu.trackizer.di.nativeModule
import com.jefisu.trackizer.feature.auth.di.AUTH_SCOPE_ID
import com.jefisu.trackizer.feature.auth.presentation.util.AuthEvent
import com.jefisu.trackizer.navigation.Destination
import com.jefisu.trackizer.navigation.NavGraph
import com.jefisu.trackizer.navigation.isCurrentDestination
import com.jefisu.trackizer.navigation.navigateSingleTopTo
import com.jefisu.trackizer.util.CoilConfig
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.ksp.generated.module
import trackizer.composeapp.generated.resources.Res
import trackizer.composeapp.generated.resources.ic_budgets
import trackizer.composeapp.generated.resources.ic_calendar
import trackizer.composeapp.generated.resources.ic_credit_cards
import trackizer.composeapp.generated.resources.ic_home
import trackizer.composeapp.generated.resources.ic_rounded_add

@Composable
@Preview
fun App(configure: (() -> Unit)? = null) = TrackizerTheme {
    val navController = rememberNavController()

    setSingletonImageLoaderFactory { context ->
        CoilConfig.buildImageLoader(context)
    }

    KoinMultiplatformApplication(
        config = KoinConfiguration {
            modules(AppModule().module, nativeModule)
        },
    ) {
        LaunchedEffect(Unit) {
            configure?.invoke()
        }

        val viewModel = koinViewModel<AppViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val userRepository = koinInject<UserRepository>()
        var isUserLoggedIn by rememberSaveable {
            mutableStateOf(userRepository.isLoggedIn())
        }

        FlashMessageDialog(
            message = state.message,
            onDismiss = viewModel::dismissMessage,
        )

        ObserveAsEvents(EventManager.events) { event ->
            if (event !is AuthEvent.UserAuthenticated) return@ObserveAsEvents
            navController.navigate(Destination.LoggedGraph) {
                popUpTo<Destination.AuthGraph> { inclusive = true }
            }
            isUserLoggedIn = true
            closeKoinScope(AUTH_SCOPE_ID)
        }

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                AppBottomNav(
                    navController = navController,
                    isUserLoggedIn = isUserLoggedIn,
                )
            },
        ) {
            NavGraph(
                startDestination = when {
                    isUserLoggedIn -> Destination.LoggedGraph
                    else -> Destination.AuthGraph
                },
                navController = navController,
            )
        }
    }
}

@Composable
fun AppBottomNav(isUserLoggedIn: Boolean, navController: NavController) {
    val navItems = remember {
        listOf(
            BottomNavItem(
                icon = Res.drawable.ic_home,
                onClick = {
                    navController.navigateSingleTopTo<Destination.HomeScreen>(
                        Destination.HomeScreen,
                    )
                },
            ),
            BottomNavItem(
                icon = Res.drawable.ic_budgets,
                onClick = {
                    navController.navigateSingleTopTo<Destination.HomeScreen>(
                        Destination.SpendingBudgetsScreen,
                    )
                },
            ),
            BottomNavItem(
                icon = Res.drawable.ic_rounded_add,
                onClick = {
                    navController.navigateSingleTopTo<Destination.HomeScreen>(
                        Destination.AddSubscriptionScreen,
                    )
                },
            ),
            BottomNavItem(
                icon = Res.drawable.ic_calendar,
                onClick = {
                    navController.navigateSingleTopTo<Destination.HomeScreen>(
                        Destination.CalendarScreen,
                    )
                },
            ),
            BottomNavItem(
                icon = Res.drawable.ic_credit_cards,
                onClick = {
                    navController.navigateSingleTopTo<Destination.HomeScreen>(
                        Destination.CreditCardsScreen,
                    )
                },
            ),
        )
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isAddSubscriptionScreenVisible by remember {
        derivedStateOf {
            navBackStackEntry?.isCurrentDestination(Destination.AddSubscriptionScreen) == true
        }
    }

    AnimatedVisibility(
        visible = isUserLoggedIn && !isAddSubscriptionScreenVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        TrackizerBottomNavBar(navItems = navItems)
    }
}
