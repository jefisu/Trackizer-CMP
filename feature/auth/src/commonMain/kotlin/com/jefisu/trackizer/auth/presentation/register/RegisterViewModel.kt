package com.jefisu.trackizer.auth.presentation.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.EmailChanged -> {
                _state.update { it.copy(email = action.email) }
            }

            is RegisterAction.PasswordChanged -> {
                _state.update { it.copy(password = action.password) }
            }

            is RegisterAction.OnRegisterClick -> register()
            else -> Unit
        }
    }

    private fun register() {
    }
}
