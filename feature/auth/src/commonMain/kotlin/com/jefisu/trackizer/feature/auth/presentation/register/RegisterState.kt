package com.jefisu.trackizer.feature.auth.presentation.register

import com.jefisu.trackizer.feature.auth.presentation.register.util.PasswordSecurityLevel

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val passwordSecurityLevel: PasswordSecurityLevel? = null,
)
