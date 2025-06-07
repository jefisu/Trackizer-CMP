package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.adamglin.composeshadow.dropShadow
import com.jefisu.trackizer.core.designsystem.AccentPrimary100
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun TrackizerButton(
    text: String,
    onClick: () -> Unit,
    type: ButtonType,
    modifier: Modifier = Modifier,
    leadingIconRes: DrawableResource? = null,
    isLoading: Boolean = false,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    enabled: Boolean = true,
) {
    val shadowModifier = if (type.hasShadow && enabled) {
        Modifier.dropShadow(
            shape = CircleShape,
            color = type.containerColor.copy(alpha = 0.5f),
            blur = 25.dp,
            offsetY = 8.dp,
        )
    } else {
        Modifier
    }

    Button(
        onClick = onClick,
        enabled = enabled,
        border = BorderStroke(
            width = 1.dp,
            brush = if (enabled) type.borderGradient else SolidColor(Color.Transparent),
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = type.containerColor,
            contentColor = type.contentColor,
            disabledContentColor = Gray50,
        ),
        contentPadding = contentPadding,
        modifier = modifier
            .widthIn(max = TrackizerTheme.size.buttonMaxWidth)
            .height(TrackizerTheme.size.buttonHeight)
            .then(shadowModifier),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(TrackizerTheme.size.circularProgressSmall),
                    strokeCap = StrokeCap.Round,
                    color = LocalContentColor.current,
                )
            } else {
                leadingIconRes?.let { res ->
                    Icon(
                        painter = painterResource(res),
                        contentDescription = null,
                        modifier = Modifier.size(TrackizerTheme.size.iconExtraSmall),
                    )
                    Spacer(Modifier.width(TrackizerTheme.spacing.small))
                }
                AutoResizedText(
                    text = text,
                    style = TrackizerTheme.typography.headline2,
                )
            }
        }
    }
}

sealed class ButtonType(
    val containerColor: Color,
    val hasShadow: Boolean = false,
    val contentColor: Color = Color.White,
    val borderGradient: Brush = SolidColor(Color.Transparent),
) {
    data object Primary : ButtonType(
        containerColor = AccentPrimary100,
        borderGradient = Brush.sweepGradient(
            0f to Color.White.copy(0.1f),
            0.8f to Color.White.copy(0.3f),
            1f to Color.Transparent,
        ),
        hasShadow = true,
    )

    data object Secondary : ButtonType(
        containerColor = Color.White.copy(0.1f),
        borderGradient = Brush.sweepGradient(
            0f to Color.White.copy(0.01f),
            0.8f to Color.White.copy(0.1f),
            1f to Color.Transparent,
        ),
    )

    data class Dynamic(val container: Color, val content: Color) :
        ButtonType(
            containerColor = container,
            contentColor = content,
            hasShadow = true,
        )
}

private class ButtonPreviewParameter : PreviewParameterProvider<ButtonType> {
    override val values get() = sequenceOf(ButtonType.Primary, ButtonType.Secondary)
}

@Preview
@Composable
private fun TrackizerButtonPreview(
    @PreviewParameter(ButtonPreviewParameter::class) type: ButtonType,
) {
    TrackizerTheme {
        TrackizerButton(
            text = "Get Started",
            onClick = {},
            type = type,
            modifier = Modifier.padding(TrackizerTheme.spacing.small),
            isLoading = false,
        )
    }
}

@Preview
@Composable
private fun LoadingTrackizerButtonPreview() {
    TrackizerTheme {
        TrackizerButton(
            text = "Get Started",
            onClick = {},
            type = ButtonType.Primary,
            modifier = Modifier.padding(TrackizerTheme.spacing.small),
            isLoading = true,
        )
    }
}
