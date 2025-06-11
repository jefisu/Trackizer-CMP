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
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP,
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
}

private typealias AnimatedTransition = AnimatedContentTransitionScope.SlideDirection

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
    ): AnimatedTransition {
        return when (animationType) {
            AnimationType.BOTTOM_TO_TOP ->
                if (isPop) AnimatedTransition.Down else AnimatedTransition.Up

            AnimationType.TOP_TO_BOTTOM ->
                if (isPop) AnimatedTransition.Up else AnimatedTransition.Down

            AnimationType.RIGHT_TO_LEFT ->
                if (isPop) AnimatedTransition.Right else AnimatedTransition.Left

            AnimationType.LEFT_TO_RIGHT ->
                if (isPop) AnimatedTransition.Left else AnimatedTransition.Right
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
            ),
            animationSpec = tween(animDuration),
        )
    }
}
