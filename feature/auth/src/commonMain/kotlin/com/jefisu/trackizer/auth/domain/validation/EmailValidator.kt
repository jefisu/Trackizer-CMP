package com.jefisu.trackizer.auth.domain.validation

import com.jefisu.trackizer.core.util.Message
import com.jefisu.trackizer.core.util.ValidationResult
import com.jefisu.trackizer.core.util.ValidationRule
import com.jefisu.trackizer.core.util.Validator
import org.koin.core.annotation.Single

@Single
class EmailValidator : Validator<String, EmailValidationError> {
    override fun validate(value: String): ValidationResult<EmailValidationError> {
        val errors = validationRules
            .filter { !it.validate(value) }
            .map { it.error }
        return ValidationResult(
            successfully = errors.isEmpty(),
            error = errors.firstOrNull(),
        )
    }
}

enum class EmailValidationError : Message {
    INVALID_FORMAT,
    CAN_T_BE_BLANK,
}

private val validationRules = listOf(
    ValidationRule<String, EmailValidationError>(
        validate = { it.isNotBlank() },
        error = EmailValidationError.CAN_T_BE_BLANK,
    ),
    ValidationRule(
        validate = { Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}").containsMatchIn(it) },
        error = EmailValidationError.INVALID_FORMAT,
    ),
)
