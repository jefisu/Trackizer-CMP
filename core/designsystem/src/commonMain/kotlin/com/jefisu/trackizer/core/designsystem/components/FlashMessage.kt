package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.jefisu.trackizer.core.designsystem.ErrorContainerColor
import com.jefisu.trackizer.core.designsystem.HelpContainerColor
import com.jefisu.trackizer.core.designsystem.SuccessContainerColor
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.WarningContainerColor
import com.jefisu.trackizer.core.ui.MessageUi
import com.jefisu.trackizer.core.ui.UiText
import compose.icons.FeatherIcons
import compose.icons.feathericons.X
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import trackizer.core.designsystem.generated.resources.Res
import trackizer.core.designsystem.generated.resources.error_flash_message
import trackizer.core.designsystem.generated.resources.help_flash_message
import trackizer.core.designsystem.generated.resources.ic_error
import trackizer.core.designsystem.generated.resources.ic_error_background
import trackizer.core.designsystem.generated.resources.ic_help
import trackizer.core.designsystem.generated.resources.ic_help_background
import trackizer.core.designsystem.generated.resources.ic_success
import trackizer.core.designsystem.generated.resources.ic_success_background
import trackizer.core.designsystem.generated.resources.ic_warning
import trackizer.core.designsystem.generated.resources.ic_warning_background
import trackizer.core.designsystem.generated.resources.success_flash_message
import trackizer.core.designsystem.generated.resources.warning_flash_message

@Composable
fun FlashMessageDialog(message: MessageUi?, onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    message?.let { msg ->
        val flashMessageType = when (message) {
            is MessageUi.Error -> FlashMessageType.ERROR
            is MessageUi.Warning -> FlashMessageType.WARNING
            is MessageUi.Help -> FlashMessageType.HELP
            is MessageUi.Success -> FlashMessageType.SUCCESS
        }

        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
            ) {
                FlashMessage(
                    message = msg.text.asString(),
                    messageType = flashMessageType,
                    onCloseClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = TrackizerTheme.spacing.extraMedium),
                )
            }
        }
    }
}

@Composable
internal fun FlashMessage(
    message: String,
    messageType: FlashMessageType,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(32.dp),
) {
    Box(
        modifier = modifier.clip(shape),
    ) {
        Image(
            painter = painterResource(messageType.iconRes),
            contentDescription = null,
            modifier = Modifier
                .zIndex(1f)
                .padding(start = 20.dp)
                .size(58.dp),
        )
        Image(
            painter = painterResource(messageType.backgroundIconRes),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .zIndex(1f)
                .width(80.dp),
        )
        Row(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
                .heightIn(min = 100.dp)
                .clip(shape)
                .background(messageType.containerColor)
                .padding(
                    start = 105.dp,
                    end = 4.dp,
                    top = 4.dp,
                    bottom = TrackizerTheme.spacing.extraSmall,
                ),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = TrackizerTheme.spacing.small),
            ) {
                Text(
                    text = stringResource(messageType.titleRes),
                    style = TrackizerTheme.typography.headline6,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.height(TrackizerTheme.spacing.small / 2))
                Text(
                    text = message,
                    style = TrackizerTheme.typography.bodyMedium,
                    maxLines = 4,
                )
            }
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = FeatherIcons.X,
                    contentDescription = null,
                )
            }
        }
    }
}

internal enum class FlashMessageType(
    val titleRes: StringResource,
    val containerColor: Color,
    val iconRes: DrawableResource,
    val backgroundIconRes: DrawableResource,
) {
    SUCCESS(
        titleRes = Res.string.success_flash_message,
        containerColor = SuccessContainerColor,
        iconRes = Res.drawable.ic_success,
        backgroundIconRes = Res.drawable.ic_success_background,
    ),
    ERROR(
        titleRes = Res.string.error_flash_message,
        containerColor = ErrorContainerColor,
        iconRes = Res.drawable.ic_error,
        backgroundIconRes = Res.drawable.ic_error_background,
    ),
    WARNING(
        titleRes = Res.string.warning_flash_message,
        containerColor = WarningContainerColor,
        iconRes = Res.drawable.ic_warning,
        backgroundIconRes = Res.drawable.ic_warning_background,
    ),
    HELP(
        titleRes = Res.string.help_flash_message,
        containerColor = HelpContainerColor,
        iconRes = Res.drawable.ic_help,
        backgroundIconRes = Res.drawable.ic_help_background,
    ),
}

@Preview
@Composable
private fun FlashMessageDialogPreview() {
    TrackizerTheme {
        FlashMessageDialog(
            message = MessageUi.Success(UiText.DynamicString(messages.first().first)),
            onDismiss = {},
            modifier = Modifier.padding(TrackizerTheme.spacing.medium),
        )
    }
}

private class FlashMessagePreviewParameter :
    PreviewParameterProvider<Pair<String, FlashMessageType>> {
    override val values: Sequence<Pair<String, FlashMessageType>>
        get() = messages.asSequence()
}

@Preview
@Composable
private fun FlashMessagePreview(
    @PreviewParameter(FlashMessagePreviewParameter::class) message: Pair<String, FlashMessageType>,
) {
    TrackizerTheme {
        FlashMessage(
            message = message.first,
            messageType = message.second,
            modifier = Modifier.fillMaxWidth(),
            onCloseClick = {},
        )
    }
}

private val messages = listOf(
    "You successfully read this important message." to FlashMessageType.SUCCESS,
    "Change a few things up and try submitting again." to FlashMessageType.ERROR,
    "Sorry! There was a problem with your request." to FlashMessageType.WARNING,
    "Do you have a problem? Just use this contact form." to FlashMessageType.HELP,
)
