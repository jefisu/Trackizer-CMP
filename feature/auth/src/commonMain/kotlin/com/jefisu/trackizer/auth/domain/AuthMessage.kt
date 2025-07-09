package com.jefisu.trackizer.auth.domain

import com.jefisu.trackizer.auth.domain.validation.EmailValidationError
import com.jefisu.trackizer.auth.domain.validation.PasswordValidationError
import com.jefisu.trackizer.core.platform.auth.AuthProviderType
import com.jefisu.trackizer.core.util.Message

sealed interface AuthMessage : Message {

    sealed interface Error : AuthMessage {

        sealed interface Validation : Error {
            data class Email(val error: EmailValidationError) : Validation
            data class Password(val error: PasswordValidationError) : Validation
        }

        data object InvalidCredentials : Error
        data object UserAlreadyExists : Error
        data object UserNotFound : Error
        data object ServerError : Error
        data object InternetUnavailable : Error

        data class ThirdPartyProvider(val type: AuthProviderType) : Error
        data class Dynamic(val message: String) : Error
    }

    sealed interface Success : AuthMessage {
        data object PasswordResetEmailSent : Success
    }
}
