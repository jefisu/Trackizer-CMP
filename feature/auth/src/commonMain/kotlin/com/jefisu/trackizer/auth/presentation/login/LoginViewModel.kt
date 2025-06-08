package com.jefisu.trackizer.auth.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.EmailChanged -> _state.update {
                it.copy(email = action.email)
            }

            is LoginAction.PasswordChanged -> _state.update {
                it.copy(password = action.password)
            }

            is LoginAction.RememberMeCredentials -> _state.update {
                it.copy(rememberMeCredentials = !it.rememberMeCredentials)
            }

            is LoginAction.Login -> login()

            is LoginAction.EmailResetPasswordChanged -> _state.update {
                it.copy(emailResetPassword = action.email)
            }

            is LoginAction.SendResetPassword -> sendResetPassword()
        }
    }

    private fun login() {
    }

    private fun sendResetPassword() {
    }
}
