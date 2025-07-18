package com.jefisu.trackizer.auth.domain.validation

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.jefisu.trackizer.core.util.ValidationResult
import com.jefisu.trackizer.feature.auth.domain.validation.EmailValidationError
import com.jefisu.trackizer.feature.auth.domain.validation.EmailValidator
import com.jefisu.trackizer.testutil.EMPTY_EMAIL
import com.jefisu.trackizer.testutil.INVALID_EMAIL
import com.jefisu.trackizer.testutil.VALID_EMAIL
import kotlin.test.BeforeTest
import kotlin.test.Test

class EmailValidatorTest {

    private lateinit var emailValidator: EmailValidator

    @BeforeTest
    fun setUp() {
        emailValidator = EmailValidator()
    }

    @Test
    fun validateEmptyEmailShouldReturnBlankError() {
        val result = validateEmail(EMPTY_EMAIL)
        assertThat(result.error).isEqualTo(EmailValidationError.CAN_T_BE_BLANK)
    }

    @Test
    fun validateEmailWithInvalidFormatShouldReturnFormatError() {
        val result = validateEmail(INVALID_EMAIL)
        assertThat(result.error).isEqualTo(EmailValidationError.INVALID_FORMAT)
    }

    @Test
    fun validateEmailWithValidFormatShouldSucceed() {
        val result = validateEmail(VALID_EMAIL)
        assertThat(result.successfully).isTrue()
        assertThat(result.error).isNull()
    }

    private fun validateEmail(email: String): ValidationResult<EmailValidationError> =
        emailValidator.validate(email)
}
