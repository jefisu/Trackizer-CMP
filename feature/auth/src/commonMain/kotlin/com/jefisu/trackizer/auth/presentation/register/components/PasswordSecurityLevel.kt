package com.jefisu.trackizer.auth.presentation.register.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.auth.presentation.register.util.PasswordSecurityLevel
import com.jefisu.trackizer.core.designsystem.AccentSecondary100
import com.jefisu.trackizer.core.designsystem.Gray70
import com.jefisu.trackizer.core.designsystem.Gray80
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun PasswordSecurityLevel(
    modifier: Modifier = Modifier,
    securityLevel: PasswordSecurityLevel? = null,
    thickness: Dp = 5.dp,
) {
    val shapes = Shapes().small
    var progress by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(key1 = securityLevel) {
        securityLevel?.let { value ->
            val selectedIndex = PasswordSecurityLevel.entries.indexOf(value)
            while (progress != selectedIndex) {
                progress += if (progress < selectedIndex) 1 else -1
                delay(300)
            }
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(TrackizerTheme.spacing.small),
    ) {
        PasswordSecurityLevel.entries.forEachIndexed { i, strength ->
            val widthAnim by animateFloatAsState(
                targetValue = if (i <= progress && securityLevel != null) 1f else 0f,
                animationSpec = tween(durationMillis = 1000),
                label = "width",
            )

            Box(
                modifier = Modifier
                    .height(thickness)
                    .weight(1f)
                    .clip(
                        shape = when (strength) {
                            PasswordSecurityLevel.WEAK -> {
                                shapes.copy(
                                    topEnd = ZeroCornerSize,
                                    bottomEnd = ZeroCornerSize,
                                )
                            }

                            PasswordSecurityLevel.STRONG -> {
                                shapes.copy(
                                    topStart = ZeroCornerSize,
                                    bottomStart = ZeroCornerSize,
                                )
                            }

                            else -> RectangleShape
                        },
                    )
                    .background(Gray70)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            color = AccentSecondary100,
                            size = size.copy(
                                width = size.width * widthAnim,
                            ),
                        )
                    },
            )
        }
    }
}

@Preview
@Composable
private fun PasswordSecurityLevelPreview() {
    var selectedSecurityLevel by remember {
        mutableStateOf<PasswordSecurityLevel?>(null)
    }

    PasswordSecurityLevel(
        securityLevel = selectedSecurityLevel,
        modifier = Modifier
            .width(300.dp)
            .background(Gray80)
            .padding(TrackizerTheme.spacing.small)
            .clickable {
                if (selectedSecurityLevel == null) {
                    selectedSecurityLevel = PasswordSecurityLevel.entries.random()
                    return@clickable
                }
                selectedSecurityLevel = null
            },
    )
}
