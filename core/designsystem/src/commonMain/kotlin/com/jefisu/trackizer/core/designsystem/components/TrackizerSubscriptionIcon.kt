package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.jefisu.trackizer.core.designsystem.Primary100
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.util.PredefinedSubscriptionServices
import com.jefisu.trackizer.core.util.ImageData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import trackizer.core.designsystem.generated.resources.Res
import trackizer.core.designsystem.generated.resources.ic_image_load_error

@Composable
fun TrackizerSubscriptionIcon(
    imageData: ImageData,
    modifier: Modifier = Modifier,
    iconSize: Dp = TrackizerTheme.size.iconDefault,
    containerSize: Dp = 40.dp,
    cornerSize: Dp = 12.dp,
    containerColor: Color? = null,
) {
    val predefinedColor = PredefinedSubscriptionServices
        .entries
        .firstOrNull { it.imageRes == (imageData as? ImageData.Device)?.resource }
        ?.color

    TrackizerIconContainer(
        containerColor = predefinedColor ?: containerColor ?: Primary100.copy(0.1f),
        containerSize = containerSize,
        cornerSize = cornerSize,
        modifier = modifier,
        content = {
            val imageModifier = Modifier.size(iconSize)
            when (imageData) {
                is ImageData.Device -> Image(
                    painter = painterResource(imageData.resource),
                    contentDescription = null,
                    modifier = imageModifier,
                )

                is ImageData.Server -> {
                    SubcomposeAsyncImage(
                        model = imageData.url,
                        contentDescription = null,
                        modifier = imageModifier,
                        loading = {
                            CircularProgressIndicator()
                        },
                        error = {
                            Image(
                                painter = painterResource(Res.drawable.ic_image_load_error),
                                contentDescription = null,
                                modifier = imageModifier,
                                colorFilter = ColorFilter.tint(Color.White),
                            )
                        },
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun SubscriptionIconPreview(
    @PreviewParameter(SubscriptionIconPreviewParameter::class) imageData: ImageData,
) {
    TrackizerTheme {
        TrackizerSubscriptionIcon(
            imageData = imageData,
        )
    }
}

private class SubscriptionIconPreviewParameter : PreviewParameterProvider<ImageData> {
    val subscriptionServicesType = PredefinedSubscriptionServices.entries[0]
    override val values = sequenceOf(
        ImageData.Device(subscriptionServicesType.imageRes),
        ImageData.Server(""),
    )
}
