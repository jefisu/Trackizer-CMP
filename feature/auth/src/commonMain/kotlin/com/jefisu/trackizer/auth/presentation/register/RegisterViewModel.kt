package com.jefisu.trackizer.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.trackizer.auth.di.AuthScope
import com.jefisu.trackizer.auth.domain.usecase.RegisterUserUseCase
import com.jefisu.trackizer.auth.domain.validation.PASSWORD_MAX_LENGTH
import com.jefisu.trackizer.auth.presentation.register.util.getSecurityLevel
import com.jefisu.trackizer.auth.presentation.util.AuthEvent
import com.jefisu.trackizer.auth.presentation.util.asMessageUi
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.MessageManager
import com.jefisu.trackizer.core.util.onError
import com.jefisu.trackizer.core.util.onSuccess
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Scope

@Scope(AuthScope::class)
@KoinViewModel
class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart { assessPasswordSecurityLevel() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5.seconds),
            _state.value,
        )

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.EmailChanged -> {
                _state.update { it.copy(email = action.email) }
            }

            is RegisterAction.PasswordChanged -> {
                if (action.password.length > PASSWORD_MAX_LENGTH) return
                _state.update {
                    it.copy(password = action.password)
                }
            }

            is RegisterAction.OnRegisterClick -> register()
            else -> Unit
        }
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            registerUserUseCase
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

    private fun assessPasswordSecurityLevel() {
        _state
            .map { it.password }
            .distinctUntilChanged()
            .onEach { password ->
                val securityLevel = getSecurityLevel(password)
                _state.update { it.copy(passwordSecurityLevel = securityLevel) }
            }
            .launchIn(viewModelScope)
    }
}
