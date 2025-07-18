package com.jefisu.trackizer.feature.auth.presentation.register

sealed interface RegisterAction {
    data class EmailChanged(val email: String) : RegisterAction
    data class PasswordChanged(val password: String) : RegisterAction
    data object OnRegisterClick : RegisterAction
    data object OnSignInClick : RegisterAction
}
