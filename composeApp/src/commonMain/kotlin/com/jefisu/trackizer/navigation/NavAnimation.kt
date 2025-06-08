package com.jefisu.trackizer.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

data class AnimationTarget(
    val destination: Destination,
    val type: AnimationType,
)

data class AnimationConfig(
    val currentDestination: Destination,
    val targets: List<AnimationTarget>,
)

enum class AnimationType {
    HORIZONTAL,
    VERTICAL,
}

class NavAnimation(
    private val animConfigs: List<AnimationConfig> = emptyList(),
    val animDuration: Int = 700,
) {

    private fun getAnimationType(
        scope: AnimatedContentTransitionScope<NavBackStackEntry>,
        isPopAnimation: Boolean,
    ): AnimationType? {
        val sourceDestination = if (isPopAnimation) scope.targetState else scope.initialState
        val targetDestination = if (isPopAnimation) scope.initialState else scope.targetState

        val config = animConfigs.firstOrNull { config ->
            sourceDestination.isCurrentDestination(config.currentDestination)
        } ?: return null

        return config.targets.firstOrNull { target ->
            targetDestination.isCurrentDestination(target.destination)
        }?.type
    }

    private fun getSlideDirection(
        animationType: AnimationType,
        isPop: Boolean,
        isEnter: Boolean,
    ): AnimatedContentTransitionScope.SlideDirection {
        return when (animationType) {
            AnimationType.HORIZONTAL -> getHorizontalSlideDirection(isPop, isEnter)
            AnimationType.VERTICAL -> getVerticalSlideDirection(isPop, isEnter)
        }
    }

    private fun getHorizontalSlideDirection(
        isPop: Boolean,
        isEnter: Boolean,
    ): AnimatedContentTransitionScope.SlideDirection {
        return if (isEnter) {
            if (isPop) {
                AnimatedContentTransitionScope.SlideDirection.Right
            } else {
                AnimatedContentTransitionScope.SlideDirection.Left
            }
        } else {
            if (isPop) {
                AnimatedContentTransitionScope.SlideDirection.Right
            } else {
                AnimatedContentTransitionScope.SlideDirection.Left
            }
        }
    }

    private fun getVerticalSlideDirection(
        isPop: Boolean,
        isEnter: Boolean,
    ): AnimatedContentTransitionScope.SlideDirection {
        return if (isEnter) {
            if (isPop) {
                AnimatedContentTransitionScope.SlideDirection.Down
            } else {
                AnimatedContentTransitionScope.SlideDirection.Up
            }
        } else {
            if (isPop) {
                AnimatedContentTransitionScope.SlideDirection.Down
            } else {
                AnimatedContentTransitionScope.SlideDirection.Up
            }
        }
    }

    fun enterTransition(
        scope: AnimatedContentTransitionScope<NavBackStackEntry>,
        isPopAnimation: Boolean = false,
    ): EnterTransition {
        val animationType = getAnimationType(scope, isPopAnimation)
            ?: return fadeIn(animationSpec = tween(animDuration))

        return scope.slideIntoContainer(
            towards = getSlideDirection(
                animationType = animationType,
                isPop = isPopAnimation,
                isEnter = true,
            ),
            animationSpec = tween(animDuration),
        )
    }

    fun exitTransition(
        scope: AnimatedContentTransitionScope<NavBackStackEntry>,
        isPopAnimation: Boolean = false,
    ): ExitTransition {
        val animationType = getAnimationType(scope, isPopAnimation)
            ?: return fadeOut(animationSpec = tween(animDuration))

        return scope.slideOutOfContainer(
            towards = getSlideDirection(
                animationType = animationType,
                isPop = isPopAnimation,
                isEnter = false,
            ),
            animationSpec = tween(animDuration),
        )
    }
}
