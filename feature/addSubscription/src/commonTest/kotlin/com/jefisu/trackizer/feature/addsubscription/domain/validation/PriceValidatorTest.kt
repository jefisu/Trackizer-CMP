package com.jefisu.trackizer.feature.addsubscription.domain.validation

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jefisu.trackizer.core.util.ValidationResult
import kotlin.test.BeforeTest
import kotlin.test.Test

class PriceValidatorTest {

    private lateinit var priceValidator: PriceValidator

    @BeforeTest
    fun setUp() {
        priceValidator = PriceValidator()
    }

    @Test
    fun validateShouldReturnSuccessForPositivePrice() {
        val price = 10.0f
        val result = priceValidator.validate(price)

        val expectedResult = ValidationResult<PriceValidationError>(
            successfully = true,
            error = null,
        )
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun validateShouldReturnErrorForZeroPrice() {
        val price = 0.0f
        val result = priceValidator.validate(price)

        val expectedResult = ValidationResult(
            successfully = false,
            error = PriceValidationError.EMPTY,
        )
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun validateShouldReturnErrorForNegativePrice() {
        val price = -5.0f
        val result = priceValidator.validate(price)

        val expectedResult = ValidationResult(
            successfully = false,
            error = PriceValidationError.EMPTY,
        )
        assertThat(result).isEqualTo(expectedResult)
    }
}
