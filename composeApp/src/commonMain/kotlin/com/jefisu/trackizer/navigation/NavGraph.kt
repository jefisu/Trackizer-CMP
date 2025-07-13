@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.jefisu.trackizer.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jefisu.trackizer.core.ui.LocalSharedTransitionScope
import com.jefisu.trackizer.navigation.graph.authGraph
import com.jefisu.trackizer.navigation.graph.authNavAnimConfig
import com.jefisu.trackizer.navigation.graph.loggedGraph
import com.jefisu.trackizer.navigation.graph.loggedNavAnimConfig

@Composable
fun NavGraph(startDestination: Destination, navController: NavHostController) {
    val navAnimationConfig = remember {
        buildList {
            addAll(authNavAnimConfig)
            addAll(loggedNavAnimConfig)
        }.map { (currentDestination, targets) ->
            AnimationConfig(currentDestination, targets)
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
                    loggedGraph()
                }
            }
        }
    }
}
