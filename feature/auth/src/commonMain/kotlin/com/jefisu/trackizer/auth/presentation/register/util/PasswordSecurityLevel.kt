package com.jefisu.trackizer.auth.presentation.register.util

import androidx.compose.ui.graphics.Color
import com.jefisu.trackizer.auth.domain.validation.PasswordValidationError
import com.jefisu.trackizer.auth.domain.validation.PasswordValidator

enum class PasswordSecurityLevel(val color: Color) {
    VULNERABLE(Color.Red),
    WEAK(Color.Yellow),
    GOOD(Color(0xFFFFA500)),
    STRONG(Color.Green),
}

fun getSecurityLevel(password: String): PasswordSecurityLevel? {
    val validationResult = PasswordValidator().validate(password)
    val totalErrorCriteria = PasswordValidationError.entries.size

    val passedChecks = totalErrorCriteria - (validationResult.error?.size ?: 0)

    return when (passedChecks) {
        5 -> PasswordSecurityLevel.STRONG
        4, 3 -> PasswordSecurityLevel.GOOD
        2 -> PasswordSecurityLevel.WEAK
        1 -> PasswordSecurityLevel.VULNERABLE
        else -> null
    }
}
