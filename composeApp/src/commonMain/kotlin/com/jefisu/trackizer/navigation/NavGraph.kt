@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.jefisu.trackizer.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jefisu.trackizer.auth.presentation.login.LoginScreenRoot
import com.jefisu.trackizer.auth.presentation.register.RegisterScreenRoot
import com.jefisu.trackizer.auth.presentation.thirdpartyauth.ThirdPartyAuthRoot
import com.jefisu.trackizer.core.ui.LocalAnimatedContentScope
import com.jefisu.trackizer.core.ui.LocalSharedTransitionScope
import com.jefisu.trackizer.welcome.WelcomeScreenRoot

@Composable
fun NavGraph(navController: NavHostController) {
    val navAnimationConfig = remember {
        listOf(
            Destination.WelcomeScreen to listOf(
                AnimationTarget(Destination.LoginScreen, AnimationType.VERTICAL),
                AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.VERTICAL),
            ),
            Destination.ThirdPartyAuthScreen to listOf(
                AnimationTarget(Destination.WelcomeScreen, AnimationType.VERTICAL),
                AnimationTarget(Destination.RegisterScreen, AnimationType.HORIZONTAL),
            ),
            Destination.LoginScreen to listOf(
                AnimationTarget(Destination.WelcomeScreen, AnimationType.VERTICAL),
                AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.HORIZONTAL),
            ),
            Destination.RegisterScreen to listOf(
                AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.HORIZONTAL),
                AnimationTarget(Destination.LoginScreen, AnimationType.HORIZONTAL),
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
            CompositionLocalProvider(LocalSharedTransitionScope provides this) {
                NavHost(
                    navController = navController,
                    startDestination = Destination.AuthGraph,
                    enterTransition = { navAnimation.enterTransition(this) },
                    exitTransition = { navAnimation.exitTransition(this) },
                    popEnterTransition = {
                        navAnimation.enterTransition(
                            this,
                            isPopAnimation = true,
                        )
                    },
                    popExitTransition = {
                        navAnimation.exitTransition(
                            this,
                            isPopAnimation = true,
                        )
                    },
                ) {
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
