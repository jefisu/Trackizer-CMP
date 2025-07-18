package com.jefisu.trackizer.feature.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.composables.core.SheetDetent
import com.composables.core.rememberModalBottomSheetState
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.ButtonType
import com.jefisu.trackizer.core.designsystem.components.TrackizerButton
import com.jefisu.trackizer.core.designsystem.components.TrackizerLogoBox
import com.jefisu.trackizer.core.designsystem.components.TrackizerPasswordTextField
import com.jefisu.trackizer.core.designsystem.components.TrackizerTextField
import com.jefisu.trackizer.core.util.applyPlatformSpecific
import com.jefisu.trackizer.feature.auth.di.rememberAuthScope
import com.jefisu.trackizer.feature.auth.presentation.login.components.ForgotPasswordBottomSheet
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import trackizer.feature.auth.generated.resources.Res
import trackizer.feature.auth.generated.resources.forgot_password
import trackizer.feature.auth.generated.resources.if_you_don_t_have_an_account_yet
import trackizer.feature.auth.generated.resources.login
import trackizer.feature.auth.generated.resources.sign_in
import trackizer.feature.auth.generated.resources.sign_up

@Composable
fun LoginScreenRoot(
    onNavigateToRegisterScreen: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(scope = rememberAuthScope()),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onAction = { action ->
            action.takeIf { it !is LoginAction.OnSignUpClick }
                ?.let(viewModel::onAction)
                ?: onNavigateToRegisterScreen()
        },
    )
}

@Composable
private fun LoginScreen(state: LoginState, onAction: (LoginAction) -> Unit) {
    val focusManager = LocalFocusManager.current

    val forgotPasswordSheetState = rememberModalBottomSheetState(initialDetent = SheetDetent.Hidden)
    ForgotPasswordBottomSheet(
        sheetState = forgotPasswordSheetState,
        state = state,
        onAction = onAction,
    )

    TrackizerLogoBox(
        modifier = Modifier
            .padding(horizontal = TrackizerTheme.spacing.extraMedium)
            .applyPlatformSpecific(
                android = { padding(bottom = TrackizerTheme.spacing.extraSmall) },
            ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .safeDrawingPadding()
                .padding(top = TrackizerTheme.spacing.extraLarge.times(.8f)),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TrackizerTextField(
                text = state.email,
                onTextChange = { onAction(LoginAction.EmailChanged(it)) },
                fieldName = stringResource(Res.string.login),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                },
            )
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
            TrackizerPasswordTextField(
                text = state.password,
                onTextChange = { onAction(LoginAction.PasswordChanged(it)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
            )
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.extraSmall))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                TextButton(
                    onClick = {
                        forgotPasswordSheetState.targetDetent = SheetDetent.FullyExpanded
                    },
                ) {
                    Text(
                        text = stringResource(Res.string.forgot_password),
                        style = TrackizerTheme.typography.bodyMedium,
                        color = Gray50,
                    )
                }
            }
            Spacer(modifier = Modifier.height(TrackizerTheme.spacing.extraSmall))
            TrackizerButton(
                text = stringResource(Res.string.sign_in),
                type = ButtonType.Primary,
                isLoading =
                state.isLoading && forgotPasswordSheetState.currentDetent == SheetDetent.Hidden,
                onClick = { onAction(LoginAction.Login) },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(Res.string.if_you_don_t_have_an_account_yet),
                style = TrackizerTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
            Spacer(modifier = Modifier.height(20.dp))
            TrackizerButton(
                text = stringResource(Res.string.sign_up),
                type = ButtonType.Secondary,
                onClick = { onAction(LoginAction.OnSignUpClick) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    TrackizerTheme {
        TrackizerLogoBox {
            LoginScreen(
                state = LoginState(),
                onAction = {},
            )
        }
    }
}
