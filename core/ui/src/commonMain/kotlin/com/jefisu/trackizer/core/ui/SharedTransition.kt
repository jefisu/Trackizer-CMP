@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.jefisu.trackizer.core.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalInspectionMode

val LocalAnimatedContentScope = compositionLocalOf<AnimatedContentScope?> { null }
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

fun Modifier.sharedTransition(
    block: @Composable SharedTransitionScope.(AnimatedContentScope) -> Modifier,
) = composed {
    val isPreviewMode = LocalInspectionMode.current
    if (isPreviewMode) return@composed this

    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: return@composed this

    val animatedContentScope = LocalAnimatedContentScope.current
        ?: return@composed this

    with(sharedTransitionScope) {
        block(animatedContentScope)
    }
}

object SharedTransitionKeys {
    const val APP_LOGO = "appLogo"
}
