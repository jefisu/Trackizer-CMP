package com.jefisu.trackizer.auth.presentation.register

import com.jefisu.trackizer.auth.presentation.register.util.PasswordSecurityLevel

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val passwordSecurityLevel: PasswordSecurityLevel? = null,
)
