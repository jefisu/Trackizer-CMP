@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.jefisu.trackizer.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jefisu.trackizer.auth.presentation.login.LoginScreenRoot
import com.jefisu.trackizer.core.ui.LocalAnimatedContentScope
import com.jefisu.trackizer.core.ui.LocalSharedTransitionScope
import com.jefisu.trackizer.welcome.WelcomeScreenRoot

@Composable
fun NavGraph(navController: NavHostController) {
    val navAnimationConfig = remember {
        listOf(
            Destination.WelcomeScreen to listOf(
                AnimationTarget(Destination.LoginScreen, AnimationType.VERTICAL),
            ),
            Destination.LoginScreen to listOf(
                AnimationTarget(Destination.WelcomeScreen, AnimationType.VERTICAL),
            ),
        ).map { (currentDestination, targets) ->
            AnimationConfig(currentDestination = currentDestination, targets = targets)
        }
    }
    val navAnimation = remember { NavAnimation(navAnimationConfig) }

    SafeNavigationArea(
        navController = navController,
        blockInteractionMillis = 1000,
    ) {
        SharedTransitionLayout {
            NavHost(
                navController = navController,
                startDestination = Destination.AuthGraph,
                enterTransition = { navAnimation.enterTransition(this) },
                exitTransition = { navAnimation.exitTransition(this) },
                popEnterTransition = { navAnimation.enterTransition(this, isPopAnimation = true) },
                popExitTransition = { navAnimation.exitTransition(this, isPopAnimation = true) },
            ) {
                navigation<Destination.AuthGraph>(
                    startDestination = Destination.WelcomeScreen,
                ) {
                    screenSharedTransition<Destination.WelcomeScreen>(
                        sharedTransitionScope = this@SharedTransitionLayout,
                    ) {
                        WelcomeScreenRoot(
                            onNavigateToSignInScreen = {
                                navController.navigate(Destination.LoginScreen)
                            },
                            onNavigateToSignUpScreen = {},
                        )
                    }
                    screenSharedTransition<Destination.LoginScreen>(
                        sharedTransitionScope = this@SharedTransitionLayout,
                    ) {
                        LoginScreenRoot(
                            onNavigateToRegisterScreen = {},
                        )
                    }
                }
            }
        }
    }
}

private inline fun <reified T : Destination> NavGraphBuilder.screenSharedTransition(
    sharedTransitionScope: SharedTransitionScope,
    noinline content: @Composable NavBackStackEntry.() -> Unit,
) {
    composable<T> { backStack ->
        CompositionLocalProvider(
            LocalSharedTransitionScope provides sharedTransitionScope,
            LocalAnimatedContentScope provides this,
        ) {
            content(backStack)
        }
    }
}
