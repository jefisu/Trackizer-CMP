package com.jefisu.trackizer.feature.auth.presentation.login

sealed interface LoginAction {
    data class EmailChanged(val email: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    data object Login : LoginAction
    data class EmailResetPasswordChanged(val email: String) : LoginAction
    data object SendResetPasswordClick : LoginAction
    data object OnSignUpClick : LoginAction
}
