package com.jefisu.trackizer.feature.addsubscription.domain.validation

import com.jefisu.trackizer.core.util.Message
import com.jefisu.trackizer.core.util.ValidationResult
import com.jefisu.trackizer.core.util.ValidationRule
import com.jefisu.trackizer.core.util.Validator
import org.koin.core.annotation.Single

@Single
class PriceValidator : Validator<Float, PriceValidationError> {
    override fun validate(value: Float): ValidationResult<PriceValidationError> {
        val errors = validationRules
            .filter { !it.validate(value) }
            .map { it.error }
        return ValidationResult(
            successfully = errors.isEmpty(),
            error = errors.firstOrNull(),
        )
    }
}

enum class PriceValidationError : Message {
    EMPTY,
}

private val validationRules = listOf(
    ValidationRule<Float, PriceValidationError>(
        validate = { it > 0f },
        error = PriceValidationError.EMPTY,
    ),
)
