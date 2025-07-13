package com.jefisu.trackizer.core.util

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

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

fun DrawScope.drawBlur(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    useCenter: Boolean,
    maxBlurArcs: Int = 20,
    cap: StrokeCap = StrokeCap.Round,
    size: Size = this.size,
) {
    for (i in 0..maxBlurArcs) {
        drawArc(
            color = color.copy(alpha = i / 900f),
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = useCenter,
            style = Stroke(
                width = 80f + (maxBlurArcs - i) * 10,
                cap = cap,
            ),
            size = size,
        )
    }
}
