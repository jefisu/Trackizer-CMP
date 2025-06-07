package com.jefisu.trackizer.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.ButtonType
import com.jefisu.trackizer.core.designsystem.components.TrackizerButton
import com.jefisu.trackizer.core.designsystem.components.TrackizerLogoBox
import com.jefisu.trackizer.core.util.applyPlatformSpecific
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import trackizer.feature.welcome.generated.resources.Res
import trackizer.feature.welcome.generated.resources.congue_malesuada_in
import trackizer.feature.welcome.generated.resources.get_started
import trackizer.feature.welcome.generated.resources.i_have_an_account
import trackizer.feature.welcome.generated.resources.shapes_to_blur
import trackizer.feature.welcome.generated.resources.welcome_asset

@Composable
fun WelcomeScreenRoot(onNavigateToSignInScreen: () -> Unit, onNavigateToSignUpScreen: () -> Unit) {
    WelcomeScreen(
        onGetStarted = onNavigateToSignUpScreen,
        onSignIn = onNavigateToSignInScreen,
    )
}

@Composable
private fun WelcomeScreen(onGetStarted: () -> Unit, onSignIn: () -> Unit) {
    TrackizerLogoBox {
        Image(
            painter = painterResource(Res.drawable.welcome_asset),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 140.dp),
        )
        Image(
            painter = painterResource(Res.drawable.shapes_to_blur),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 140.dp)
                .blur(100.dp)
                .rotate(15f),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .safeDrawingPadding()
                .align(Alignment.BottomCenter)
                .applyPlatformSpecific(
                    android = { padding(bottom = TrackizerTheme.spacing.extraSmall) },
                )
                .padding(horizontal = TrackizerTheme.spacing.extraMedium),
        ) {
            Text(
                text = stringResource(Res.string.congue_malesuada_in),
                textAlign = TextAlign.Center,
                style = TrackizerTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(40.dp))
            TrackizerButton(
                text = stringResource(Res.string.get_started),
                type = ButtonType.Primary,
                onClick = onGetStarted,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
            TrackizerButton(
                text = stringResource(Res.string.i_have_an_account),
                type = ButtonType.Secondary,
                onClick = onSignIn,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    TrackizerTheme {
        WelcomeScreen(
            onGetStarted = {},
            onSignIn = {},
        )
    }
}
