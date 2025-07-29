package com.jefisu.trackizer.core.designsystem

import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

object TrackizerTheme {
    val typography: Typography
        @Composable
        get() = interTypography()

    val size: Size
        @Composable
        get() = LocalSize.current

    val spacing get() = Spacing()
}

@Composable
fun TrackizerTheme(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    ProvideTextStyle(
        value = TrackizerTheme.typography.bodyLarge,
    ) {
        Surface(
            modifier = modifier,
            color = Gray80,
            contentColor = Color.White,
            content = content,
        )
    }
}
