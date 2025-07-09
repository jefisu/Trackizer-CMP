package com.jefisu.trackizer.auth.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jefisu.trackizer.auth.di.rememberAuthScope
import com.jefisu.trackizer.auth.presentation.register.components.PasswordSecurityLevel
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.ButtonType
import com.jefisu.trackizer.core.designsystem.components.TrackizerButton
import com.jefisu.trackizer.core.designsystem.components.TrackizerLogoBox
import com.jefisu.trackizer.core.designsystem.components.TrackizerPasswordTextField
import com.jefisu.trackizer.core.designsystem.components.TrackizerTextField
import com.jefisu.trackizer.core.util.applyPlatformSpecific
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import trackizer.core.ui.generated.resources.Res as UiRes
import trackizer.core.ui.generated.resources.email
import trackizer.feature.auth.generated.resources.Res
import trackizer.feature.auth.generated.resources.do_you_have_already_an_account
import trackizer.feature.auth.generated.resources.get_started_it_s_free
import trackizer.feature.auth.generated.resources.sign_in
import trackizer.feature.auth.generated.resources.use_8_or_more_characters

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(scope = rememberAuthScope()),
    onNavigateToLoginScreen: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreen(
        state = state,
        onAction = { action ->
            action.takeIf { it !is RegisterAction.OnSignInClick }
                ?.let(viewModel::onAction)
                ?: onNavigateToLoginScreen()
        },
    )
}

@Composable
private fun RegisterScreen(state: RegisterState, onAction: (RegisterAction) -> Unit) {
    val focusManager = LocalFocusManager.current

    TrackizerLogoBox(
        modifier = Modifier
            .padding(horizontal = TrackizerTheme.spacing.extraMedium)
            .applyPlatformSpecific(
                android = { padding(bottom = TrackizerTheme.spacing.extraSmall) },
            ),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .safeDrawingPadding()
                .padding(top = TrackizerTheme.spacing.extraLarge.times(.8f)),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TrackizerTextField(
                text = state.email,
                onTextChange = { onAction(RegisterAction.EmailChanged(it)) },
                fieldName = stringResource(UiRes.string.email),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                },
            )
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
            TrackizerPasswordTextField(
                text = state.password,
                onTextChange = { onAction(RegisterAction.PasswordChanged(it)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
            )
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.extraMedium))
            PasswordSecurityLevel(
                securityLevel = state.passwordSecurityLevel,
            )
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
            Text(
                text = stringResource(Res.string.use_8_or_more_characters),
                style = TrackizerTheme.typography.bodySmall,
                color = Gray50,
                textAlign = TextAlign.Justify,
            )
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
            TrackizerButton(
                text = stringResource(Res.string.get_started_it_s_free),
                type = ButtonType.Primary,
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                },
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(Res.string.do_you_have_already_an_account),
                style = TrackizerTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.extraMedium))
            TrackizerButton(
                text = stringResource(Res.string.sign_in),
                type = ButtonType.Secondary,
                onClick = { onAction(RegisterAction.OnSignInClick) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    TrackizerTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {},
        )
    }
}
