package com.jefisu.trackizer.auth.domain.validation

import com.jefisu.trackizer.core.util.Message
import com.jefisu.trackizer.core.util.ValidationResult
import com.jefisu.trackizer.core.util.Validator
import org.koin.core.annotation.Single

@Single
class EmailValidator : Validator<String, EmailValidationError> {
    override fun validate(value: String): ValidationResult<EmailValidationError> {
        val errors = mutableListOf<EmailValidationError>()

        if (value.isBlank()) errors.add(EmailValidationError.CAN_T_BE_BLANK)

        val emailRegex = Regex(
            pattern = """^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$""",
            options = setOf(RegexOption.IGNORE_CASE),
        )
        if (!emailRegex.matches(value)) errors.add(EmailValidationError.INVALID_FORMAT)

        if (errors.isNotEmpty()) return ValidationResult(successfully = false, errors.first())

        return ValidationResult(successfully = true)
    }
}

enum class EmailValidationError : Message {
    INVALID_FORMAT,
    CAN_T_BE_BLANK,
}
