package com.jefisu.trackizer.feature.auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val emailResetPassword: String = "",
    val isLoadingResetPassword: Boolean = false,
)
