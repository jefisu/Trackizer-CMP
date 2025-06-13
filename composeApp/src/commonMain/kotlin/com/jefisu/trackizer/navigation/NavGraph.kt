@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.jefisu.trackizer.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.composeunstyled.Text
import com.jefisu.trackizer.auth.presentation.login.LoginScreenRoot
import com.jefisu.trackizer.auth.presentation.register.RegisterScreenRoot
import com.jefisu.trackizer.auth.presentation.thirdpartyauth.ThirdPartyAuthRoot
import com.jefisu.trackizer.auth.presentation.util.AuthEvent
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.LocalAnimatedContentScope
import com.jefisu.trackizer.core.ui.LocalSharedTransitionScope
import com.jefisu.trackizer.core.ui.ObserveAsEvents
import com.jefisu.trackizer.welcome.WelcomeScreenRoot

@Composable
fun NavGraph(startDestination: Destination, navController: NavHostController) {
    val navAnimationConfig = remember {
        listOf(
            Destination.WelcomeScreen to listOf(
                AnimationTarget(Destination.LoginScreen, AnimationType.BOTTOM_TO_TOP),
                AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.BOTTOM_TO_TOP),
            ),
            Destination.ThirdPartyAuthScreen to listOf(
                AnimationTarget(Destination.RegisterScreen, AnimationType.RIGHT_TO_LEFT),
                AnimationTarget(Destination.HomeScreen, AnimationType.BOTTOM_TO_TOP),
            ),
            Destination.LoginScreen to listOf(
                AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.TOP_TO_BOTTOM),
                AnimationTarget(Destination.HomeScreen, AnimationType.BOTTOM_TO_TOP),
            ),
            Destination.RegisterScreen to listOf(
                AnimationTarget(Destination.LoginScreen, AnimationType.LEFT_TO_RIGHT),
                AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.TOP_TO_BOTTOM),
            ),
        ).map { (currentDestination, targets) ->
            AnimationConfig(currentDestination = currentDestination, targets = targets)
        }
    }
    val navAnimation = remember { NavAnimation(navAnimationConfig) }

    ObserveAsEvents(EventManager.events) { event ->
        if (event is AuthEvent.UserAuthenticated) {
            navController.navigate(Destination.AuthenticatedGraph) {
                popUpTo<Destination.AuthGraph> {
                    inclusive = true
                }
            }
        }
    }

    SafeNavigationArea(
        navController = navController,
        blockInteractionMillis = 1000,
    ) {
        SharedTransitionLayout {
            CompositionLocalProvider(LocalSharedTransitionScope provides this) {
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = navAnimation::enterTransition,
                    exitTransition = navAnimation::exitTransition,
                    popEnterTransition = {
                        navAnimation.enterTransition(scope = this, isPopAnimation = true)
                    },
                    popExitTransition = {
                        navAnimation.exitTransition(scope = this, isPopAnimation = true)
                    },
                ) {
                    authGraph(navController)
                    authenticatedGraph()
                }
            }
        }
    }
}

private fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation<Destination.AuthGraph>(
        startDestination = Destination.WelcomeScreen,
    ) {
        animatedScreen<Destination.WelcomeScreen> {
            WelcomeScreenRoot(
                onNavigateToSignInScreen = {
                    navController.navigate(Destination.LoginScreen)
                },
                onNavigateToSignUpScreen = {
                    navController.navigate(Destination.ThirdPartyAuthScreen)
                },
            )
        }
        animatedScreen<Destination.ThirdPartyAuthScreen> {
            ThirdPartyAuthRoot(
                onNavigateToRegisterScreen = {
                    navController.navigate(Destination.RegisterScreen)
                },
            )
        }
        animatedScreen<Destination.LoginScreen> {
            LoginScreenRoot(
                onNavigateToRegisterScreen = {
                    navController.navigate(Destination.ThirdPartyAuthScreen)
                },
            )
        }
        animatedScreen<Destination.RegisterScreen> {
            RegisterScreenRoot(
                onNavigateToLoginScreen = {
                    navController.navigate(Destination.LoginScreen)
                },
            )
        }
    }
}

private fun NavGraphBuilder.authenticatedGraph() {
    navigation<Destination.AuthenticatedGraph>(
        startDestination = Destination.HomeScreen,
    ) {
        animatedScreen<Destination.HomeScreen> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text("Home Screen", color = Color.White)
            }
        }
    }
}

private inline fun <reified T : Destination> NavGraphBuilder.animatedScreen(
    noinline content: @Composable NavBackStackEntry.() -> Unit,
) {
    composable<T> { backStack ->
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            content(backStack)
        }
    }
}
