package com.jefisu.trackizer.feature.auth.presentation.register.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.Gray70
import com.jefisu.trackizer.core.designsystem.Gray80
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.feature.auth.presentation.register.util.PasswordSecurityLevel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
internal fun PasswordSecurityLevel(
    securityLevel: PasswordSecurityLevel?,
    modifier: Modifier = Modifier,
    thickness: Dp = 5.dp,
) {
    val isPreview = LocalInspectionMode.current
    val levels = PasswordSecurityLevel.entries
    val targetIndex = levels.indexOf(securityLevel)

    val barFillProgress = remember(levels.size) {
        levels.mapIndexed { index, _ ->
            Animatable(if (isPreview && index <= targetIndex) 1f else 0f)
        }
    }

    val animatedColor by animateColorAsState(
        targetValue = securityLevel?.color?.copy(alpha = 0.6f) ?: Color.Transparent,
    )

    LaunchedEffect(securityLevel) {
        if (isPreview) return@LaunchedEffect

        if (securityLevel == null) {
            // Reset all progress to 0
            levels.indices.reversed().forEach { i ->
                barFillProgress[i].animateTo(0f)
            }
            return@LaunchedEffect
        }

        // Animate each bar based on the current security level
        levels.indices.forEach { i ->
            val shouldFill = i <= targetIndex
            barFillProgress[i].animateTo(if (shouldFill) 1f else 0f)
        }
    }

    val shapes = Shapes().small

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(TrackizerTheme.spacing.small),
    ) {
        levels.forEachIndexed { i, level ->
            val widthAnim = barFillProgress[i].value

            Box(
                modifier = Modifier
                    .height(thickness)
                    .weight(1f)
                    .clip(
                        shape = when (level) {
                            PasswordSecurityLevel.VULNERABLE -> shapes.copy(
                                topEnd = ZeroCornerSize,
                                bottomEnd = ZeroCornerSize,
                            )

                            PasswordSecurityLevel.STRONG -> shapes.copy(
                                topStart = ZeroCornerSize,
                                bottomStart = ZeroCornerSize,
                            )

                            else -> RectangleShape
                        },
                    )
                    .background(Gray70)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            color = animatedColor.copy(alpha = 0.6f),
                            size = size.copy(width = size.width * widthAnim),
                        )
                    },
            )
        }
    }
}

@Preview
@Composable
private fun PasswordSecurityLevelPreview(
    @PreviewParameter(PasswordSecurityLevelPreviewParameter::class)
    securityLevel: PasswordSecurityLevel,
) {
    PasswordSecurityLevel(
        securityLevel = securityLevel,
        modifier = Modifier
            .width(300.dp)
            .background(Gray80)
            .padding(TrackizerTheme.spacing.small),
    )
}

private class PasswordSecurityLevelPreviewParameter :
    PreviewParameterProvider<PasswordSecurityLevel> {
    override val values: Sequence<PasswordSecurityLevel>
        get() = PasswordSecurityLevel.entries.asSequence()

    override val count: Int = PasswordSecurityLevel.entries.size
}
