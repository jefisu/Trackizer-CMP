package com.jefisu.trackizer.auth.presentation.thirdpartyauth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.composeunstyled.Text
import com.jefisu.trackizer.core.designsystem.FacebookColor
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.Gray80
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.ButtonType
import com.jefisu.trackizer.core.designsystem.components.TrackizerButton
import com.jefisu.trackizer.core.designsystem.components.TrackizerLogoBox
import com.jefisu.trackizer.core.util.applyPlatformSpecific
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import trackizer.feature.auth.generated.resources.Res
import trackizer.feature.auth.generated.resources.by_registering_you_agree
import trackizer.feature.auth.generated.resources.ic_facebook
import trackizer.feature.auth.generated.resources.ic_google
import trackizer.feature.auth.generated.resources.or
import trackizer.feature.auth.generated.resources.sign_up_with

@Composable
fun ThirdPartyAuthRoot(
    viewModel: ThirdPartyAuthViewModel = viewModel { ThirdPartyAuthViewModel() },
    onNavigateToRegisterScreen: () -> Unit,
) {
    ThirdPartyAuthScreen(
        onAction = { action ->
            action.takeIf { it !is ThirdPartyAuthAction.OnSignUpEmailClick }
                ?.let(viewModel::onAction)
                ?: onNavigateToRegisterScreen()
        },
    )
}

@Composable
private fun ThirdPartyAuthScreen(onAction: (ThirdPartyAuthAction) -> Unit) {
    TrackizerLogoBox {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = TrackizerTheme.spacing.extraMedium)
                .applyPlatformSpecific(
                    android = { padding(bottom = TrackizerTheme.spacing.extraSmall) },
                ),
        ) {
            TrackizerButton(
                text = stringResource(Res.string.sign_up_with, "Google"),
                type = ButtonType.Dynamic(
                    container = Color.White,
                    content = Gray80,
                ),
                leadingIconRes = Res.drawable.ic_google,
                onClick = { onAction(ThirdPartyAuthAction.OnGoogleClick) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(TrackizerTheme.spacing.medium))
            TrackizerButton(
                text = stringResource(Res.string.sign_up_with, "Facebook"),
                type = ButtonType.Dynamic(
                    container = FacebookColor,
                    content = Color.White,
                ),
                leadingIconRes = Res.drawable.ic_facebook,
                onClick = { onAction(ThirdPartyAuthAction.OnFacebookClick) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(TrackizerTheme.spacing.large))
            Text(
                text = stringResource(Res.string.or),
                style = TrackizerTheme.typography.headline2,
                color = Color.White,
            )
            Spacer(Modifier.height(TrackizerTheme.spacing.large))
            TrackizerButton(
                text = stringResource(Res.string.sign_up_with, "E-mail"),
                type = ButtonType.Secondary,
                onClick = { onAction(ThirdPartyAuthAction.OnSignUpEmailClick) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(TrackizerTheme.spacing.extraMedium))
            Text(
                text = stringResource(Res.string.by_registering_you_agree),
                style = TrackizerTheme.typography.bodySmall,
                color = Gray50,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun ThirdPartyAuthScreenPreview() {
    TrackizerTheme {
        ThirdPartyAuthScreen(
            onAction = {},
        )
    }
}
