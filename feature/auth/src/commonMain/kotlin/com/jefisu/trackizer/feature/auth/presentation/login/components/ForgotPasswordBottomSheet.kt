package com.jefisu.trackizer.feature.auth.presentation.login.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.composables.core.ModalBottomSheetState
import com.composables.core.SheetDetent
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.ButtonType
import com.jefisu.trackizer.core.designsystem.components.TrackizerBottomSheet
import com.jefisu.trackizer.core.designsystem.components.TrackizerButton
import com.jefisu.trackizer.core.designsystem.components.TrackizerTextField
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.ObserveAsEvents
import com.jefisu.trackizer.feature.auth.presentation.login.LoginAction
import com.jefisu.trackizer.feature.auth.presentation.login.LoginEvent
import com.jefisu.trackizer.feature.auth.presentation.login.LoginState
import org.jetbrains.compose.resources.stringResource
import trackizer.core.ui.generated.resources.Res as UiRes
import trackizer.core.ui.generated.resources.email
import trackizer.feature.auth.generated.resources.Res
import trackizer.feature.auth.generated.resources.forgot_your_password
import trackizer.feature.auth.generated.resources.reset_instructions_will_be_send_to_your_email
import trackizer.feature.auth.generated.resources.send

@Composable
internal fun ForgotPasswordBottomSheet(
    sheetState: ModalBottomSheetState,
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    ObserveAsEvents(EventManager.events) { event ->
        if (event is LoginEvent.DismissForgotPasswordBottomSheet) {
            sheetState.targetDetent = SheetDetent.Hidden
        }
    }

    TrackizerBottomSheet(
        sheetState = sheetState,
        onDismiss = { },
    ) {
        Text(
            text = stringResource(Res.string.forgot_your_password),
            style = TrackizerTheme.typography.headline5,
        )
        Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
        Text(
            text = stringResource(Res.string.reset_instructions_will_be_send_to_your_email),
            style = TrackizerTheme.typography.bodyMedium,
            color = Gray50,
        )
        Spacer(modifier = Modifier.height(TrackizerTheme.spacing.extraMedium))
        TrackizerTextField(
            text = state.emailResetPassword,
            onTextChange = { onAction(LoginAction.EmailResetPasswordChanged(it)) },
            fieldName = stringResource(UiRes.string.email),
        )
        Spacer(modifier = Modifier.height(TrackizerTheme.spacing.extraMedium))
        TrackizerButton(
            text = stringResource(Res.string.send),
            onClick = { onAction(LoginAction.SendResetPasswordClick) },
            type = ButtonType.Primary,
            isLoading = state.isLoadingResetPassword,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
