package com.jefisu.trackizer.feature.auth.domain.validation

import com.jefisu.trackizer.core.util.Message
import com.jefisu.trackizer.core.util.ValidationResult
import com.jefisu.trackizer.core.util.ValidationRule
import com.jefisu.trackizer.core.util.Validator
import com.jefisu.trackizer.feature.auth.di.AuthScope
import org.koin.core.annotation.Scope

@Scope(AuthScope::class)
class PasswordValidator : Validator<String, List<PasswordValidationError>> {
    override fun validate(value: String): ValidationResult<List<PasswordValidationError>> {
        val errors = validationRules
            .filter { !it.validate(value) }
            .map { it.error }
        return ValidationResult(
            successfully = errors.isEmpty(),
            error = errors.ifEmpty { null },
        )
    }
}

enum class PasswordValidationError : Message {
    TOO_SHORT,
    NO_UPPERCASE_LETTER,
    NO_LOWERCASE_LETTER,
    NO_DIGIT,
    NO_SPECIAL_CHARACTER,
}

const val PASSWORD_MIN_LENGTH = 8
const val PASSWORD_MAX_LENGTH = 24

private val validationRules = listOf(
    ValidationRule<String, PasswordValidationError>(
        validate = { it.length >= PASSWORD_MIN_LENGTH },
        error = PasswordValidationError.TOO_SHORT,
    ),
    ValidationRule(
        validate = { it.any { it.isUpperCase() } },
        error = PasswordValidationError.NO_UPPERCASE_LETTER,
    ),
    ValidationRule(
        validate = { it.any { it.isLowerCase() } },
        error = PasswordValidationError.NO_LOWERCASE_LETTER,
    ),
    ValidationRule(
        validate = { it.any { it.isDigit() } },
        error = PasswordValidationError.NO_DIGIT,
    ),
    ValidationRule(
        validate = { Regex("[^a-zA-Z0-9]").containsMatchIn(it) },
        error = PasswordValidationError.NO_SPECIAL_CHARACTER,
    ),
)
