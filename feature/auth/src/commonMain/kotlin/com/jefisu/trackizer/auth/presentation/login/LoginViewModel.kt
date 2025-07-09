package com.jefisu.trackizer.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.trackizer.auth.di.AuthScope
import com.jefisu.trackizer.auth.domain.usecase.LoginUserUseCase
import com.jefisu.trackizer.auth.domain.usecase.SendResetPasswordLinkUseCase
import com.jefisu.trackizer.auth.presentation.util.AuthEvent
import com.jefisu.trackizer.auth.presentation.util.asMessageUi
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.MessageManager
import com.jefisu.trackizer.core.util.onError
import com.jefisu.trackizer.core.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Scope

@Scope(AuthScope::class)
@KoinViewModel
class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val sendResetPasswordLinkUseCase: SendResetPasswordLinkUseCase,
) : ViewModel() {

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

            is LoginAction.Login -> login()

            is LoginAction.EmailResetPasswordChanged -> _state.update {
                it.copy(emailResetPassword = action.email)
            }

            is LoginAction.SendResetPasswordClick -> sendResetPassword()

            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            loginUserUseCase
                .execute(_state.value.email, _state.value.password)
                .onSuccess {
                    EventManager.sendEvent(AuthEvent.UserAuthenticated)
                }
                .onError { authError ->
                    MessageManager.showMessage(authError.asMessageUi())
                }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun sendResetPassword() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingResetPassword = true) }

            sendResetPasswordLinkUseCase
                .execute(_state.value.emailResetPassword)
                .onSuccess { authSuccess ->
                    MessageManager.showMessage(authSuccess.asMessageUi())
                }
                .onError { authError ->
                    MessageManager.showMessage(authError.asMessageUi())
                }

            _state.update { it.copy(isLoadingResetPassword = false) }
        }
    }
}
