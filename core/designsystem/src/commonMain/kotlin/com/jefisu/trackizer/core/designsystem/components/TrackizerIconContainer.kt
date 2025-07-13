package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.Gray30
import com.jefisu.trackizer.core.designsystem.Gray70
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TrackizerIconContainer(
    modifier: Modifier = Modifier,
    containerColor: Color = Gray70,
    contentColor: Color = Gray30,
    containerSize: Dp = TrackizerTheme.size.iconMedium,
    cornerSize: Dp = 12.dp,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(containerSize)
                .clip(RoundedCornerShape(cornerSize))
                .background(containerColor),
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun TrackizerIconContainerPreview() {
    TrackizerTheme {
        TrackizerIconContainer(
            modifier = Modifier.padding(TrackizerTheme.spacing.small),
        ) {
            Icon(
                imageVector = FeatherIcons.Check,
                contentDescription = null,
            )
        }
    }
}
