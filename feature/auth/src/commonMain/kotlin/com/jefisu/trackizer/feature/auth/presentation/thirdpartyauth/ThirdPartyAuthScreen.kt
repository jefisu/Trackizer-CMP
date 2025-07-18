package com.jefisu.trackizer.feature.auth.presentation.thirdpartyauth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composeunstyled.Text
import com.jefisu.trackizer.core.designsystem.FacebookBorderGradient
import com.jefisu.trackizer.core.designsystem.FacebookColor
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.Gray80
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.ButtonType
import com.jefisu.trackizer.core.designsystem.components.TrackizerButton
import com.jefisu.trackizer.core.designsystem.components.TrackizerLogoBox
import com.jefisu.trackizer.core.platform.auth.AuthProviderType
import com.jefisu.trackizer.core.util.applyPlatformSpecific
import com.jefisu.trackizer.core.util.runOnPlatform
import com.jefisu.trackizer.feature.auth.di.rememberAuthScope
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import trackizer.feature.auth.generated.resources.Res
import trackizer.feature.auth.generated.resources.by_registering_you_agree
import trackizer.feature.auth.generated.resources.continue_with
import trackizer.feature.auth.generated.resources.ic_facebook
import trackizer.feature.auth.generated.resources.ic_google
import trackizer.feature.auth.generated.resources.or

@Composable
fun ThirdPartyAuthRoot(
    onNavigateToRegisterScreen: () -> Unit,
    viewModel: ThirdPartyAuthViewModel = koinViewModel(scope = rememberAuthScope()),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ThirdPartyAuthScreen(
        state = state,
        onAction = { action ->
            action.takeIf { it !is ThirdPartyAuthAction.OnSignUpEmailClick }
                ?.let(viewModel::onAction)
                ?: onNavigateToRegisterScreen()
        },
    )
}

@Composable
private fun ThirdPartyAuthScreen(
    state: ThirdPartyAuthState,
    onAction: (ThirdPartyAuthAction) -> Unit,
) {
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
                text = stringResource(Res.string.continue_with, "Google"),
                type = ButtonType.Dynamic(
                    container = Color.White,
                    content = Gray80,
                ),
                leadingIconRes = Res.drawable.ic_google,
                isLoading = state.providerLoading == AuthProviderType.GOOGLE,
                onClick = {
                    onAction(ThirdPartyAuthAction.OnProviderClick(AuthProviderType.GOOGLE))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            runOnPlatform(
                android = {
                    Spacer(Modifier.height(TrackizerTheme.spacing.medium))
                    TrackizerButton(
                        text = stringResource(Res.string.continue_with, "Facebook"),
                        type = ButtonType.Dynamic(
                            container = FacebookColor,
                            content = Color.White,
                            border = FacebookBorderGradient,
                        ),
                        leadingIconRes = Res.drawable.ic_facebook,
                        isLoading = state.providerLoading == AuthProviderType.FACEBOOK,
                        onClick = {
                            onAction(
                                ThirdPartyAuthAction.OnProviderClick(AuthProviderType.FACEBOOK),
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            )
            Spacer(Modifier.height(TrackizerTheme.spacing.large))
            Text(
                text = stringResource(Res.string.or),
                style = TrackizerTheme.typography.headline2,
                color = Color.White,
            )
            Spacer(Modifier.height(TrackizerTheme.spacing.large))
            TrackizerButton(
                text = stringResource(Res.string.continue_with, "E-mail"),
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
            state = ThirdPartyAuthState(),
            onAction = {},
        )
    }
}
