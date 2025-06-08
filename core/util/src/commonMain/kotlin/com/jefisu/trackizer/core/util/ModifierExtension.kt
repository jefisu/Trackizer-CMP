package com.jefisu.trackizer.core.util

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.IntOffset

fun Modifier.applyPlatformSpecific(
    ios: Modifier.() -> Modifier = { this },
    android: Modifier.() -> Modifier = { this },
    desktop: Modifier.() -> Modifier = { this },
): Modifier {
    return when (getPlatform()) {
        Platform.IOS -> ios()
        Platform.ANDROID -> android()
        Platform.DESKTOP -> desktop()
    }
}

fun Modifier.imeOffset(imeThresholdPercent: Float = 1f) = composed {
    val imePadding = WindowInsets.ime.asPaddingValues()
    offset {
        IntOffset.Zero.copy(
            y = -imePadding.calculateBottomPadding().times(imeThresholdPercent).roundToPx(),
        )
    }
}

fun Modifier.rippleClickable(
    enabled: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: () -> Unit,
) = composed {
    combinedClickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = LocalIndication.current,
        enabled = enabled,
        onClick = onClick,
        onLongClick = onLongClick,
    )
}
