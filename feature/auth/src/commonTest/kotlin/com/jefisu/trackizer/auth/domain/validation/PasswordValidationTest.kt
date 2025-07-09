package com.jefisu.trackizer.auth.domain.validation

import assertk.assertThat
import assertk.assertions.containsAtLeast
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.jefisu.trackizer.core.util.ValidationResult
import com.jefisu.trackizer.testutil.EMPTY_PASSWORD
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_NO_DIGIT
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_NO_LOWERCASE
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_NO_SPECIAL_CHAR
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_NO_UPPERCASE
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_SHORT
import com.jefisu.trackizer.testutil.VALID_PASSWORD
import kotlin.test.BeforeTest
import kotlin.test.Test

class PasswordValidationTest {

    private lateinit var passwordValidator: PasswordValidator

    @BeforeTest
    fun setUp() {
        passwordValidator = PasswordValidator()
    }

    @Test
    fun validateEmptyPasswordShouldReturnTooShortError() {
        val result = validatePassword(EMPTY_PASSWORD)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.TOO_SHORT)
    }

    @Test
    fun validatePasswordShorterThan8CharactersShouldReturnTooShortError() {
        val result = validatePassword(INVALID_PASSWORD_SHORT)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.TOO_SHORT)
    }

    @Test
    fun validatePasswordWithoutUppercaseLetterShouldReturnNoUppercaseError() {
        val result = validatePassword(INVALID_PASSWORD_NO_UPPERCASE)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.NO_UPPERCASE_LETTER)
    }

    @Test
    fun validatePasswordWithoutLowercaseLetterShouldReturnNoLowercaseError() {
        val result = validatePassword(INVALID_PASSWORD_NO_LOWERCASE)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.NO_LOWERCASE_LETTER)
    }

    @Test
    fun validatePasswordWithoutDigitShouldReturnNoDigitError() {
        val result = validatePassword(INVALID_PASSWORD_NO_DIGIT)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.NO_DIGIT)
    }

    @Test
    fun validatePasswordWithoutSpecialCharacterShouldReturnNoSpecialCharacterError() {
        val result = validatePassword(INVALID_PASSWORD_NO_SPECIAL_CHAR)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.NO_SPECIAL_CHARACTER)
    }

    @Test
    fun validatePasswordWithAllRequirementsShouldSucceed() {
        val result = validatePassword(VALID_PASSWORD)
        assertThat(result.successfully).isTrue()
        assertThat(result.error).isNull()
    }

    private fun validatePassword(
        password: String,
    ): ValidationResult<List<PasswordValidationError>> = passwordValidator.validate(password)
}
