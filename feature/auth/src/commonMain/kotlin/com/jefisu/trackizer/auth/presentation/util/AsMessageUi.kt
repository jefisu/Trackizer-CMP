package com.jefisu.trackizer.auth.presentation.util

import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.validation.EmailValidationError
import com.jefisu.trackizer.auth.domain.validation.PasswordValidationError
import com.jefisu.trackizer.core.ui.MessageUi
import com.jefisu.trackizer.core.ui.UiText
import trackizer.feature.auth.generated.resources.Res
import trackizer.feature.auth.generated.resources.email_cant_be_blank_error
import trackizer.feature.auth.generated.resources.internet_unavailable_error
import trackizer.feature.auth.generated.resources.invalid_email_format_error
import trackizer.feature.auth.generated.resources.invalid_email_or_password_error
import trackizer.feature.auth.generated.resources.password_no_digit_error
import trackizer.feature.auth.generated.resources.password_no_lowercase_letter_error
import trackizer.feature.auth.generated.resources.password_no_special_character_error
import trackizer.feature.auth.generated.resources.password_no_uppercase_letter_error
import trackizer.feature.auth.generated.resources.password_reset_email_sent
import trackizer.feature.auth.generated.resources.password_too_short_error
import trackizer.feature.auth.generated.resources.server_error
import trackizer.feature.auth.generated.resources.user_already_exists_error
import trackizer.feature.auth.generated.resources.user_not_found_error

fun AuthMessage.asMessageUi(): MessageUi = when (this) {
    is AuthMessage.Error -> MessageUi.Error(toUiText())
    AuthMessage.Success.PasswordResetEmailSent -> MessageUi.Success(
        UiText.StringRes(Res.string.password_reset_email_sent),
    )
}

private fun AuthMessage.Error.Validation.Email.toUiText(): UiText = when (error) {
    EmailValidationError.INVALID_FORMAT -> UiText.StringRes(Res.string.invalid_email_format_error)
    EmailValidationError.CAN_T_BE_BLANK -> UiText.StringRes(Res.string.email_cant_be_blank_error)
}

private fun AuthMessage.Error.Validation.Password.toUiText(): UiText = when (error) {
    PasswordValidationError.TOO_SHORT -> UiText.StringRes(Res.string.password_too_short_error)
    PasswordValidationError.NO_UPPERCASE_LETTER -> UiText.StringRes(
        Res.string.password_no_uppercase_letter_error,
    )
    PasswordValidationError.NO_LOWERCASE_LETTER -> UiText.StringRes(
        Res.string.password_no_lowercase_letter_error,
    )
    PasswordValidationError.NO_DIGIT -> UiText.StringRes(Res.string.password_no_digit_error)
    PasswordValidationError.NO_SPECIAL_CHARACTER -> UiText.StringRes(
        Res.string.password_no_special_character_error,
    )
}

private fun AuthMessage.Error.toUiText(): UiText = when (this) {
    is AuthMessage.Error.InternetUnavailable -> UiText.StringRes(
        Res.string.internet_unavailable_error,
    )
    is AuthMessage.Error.InvalidCredentials -> UiText.StringRes(
        Res.string.invalid_email_or_password_error,
    )
    is AuthMessage.Error.ServerError -> UiText.StringRes(Res.string.server_error)
    is AuthMessage.Error.UserAlreadyExists -> UiText.StringRes(Res.string.user_already_exists_error)
    is AuthMessage.Error.UserNotFound -> UiText.StringRes(Res.string.user_not_found_error)
    is AuthMessage.Error.Validation.Email -> toUiText()
    is AuthMessage.Error.Validation.Password -> toUiText()
}
