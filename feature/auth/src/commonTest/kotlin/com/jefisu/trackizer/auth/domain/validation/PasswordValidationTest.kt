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
    fun `validate empty password should return too short error`() {
        val result = validatePassword(EMPTY_PASSWORD)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.TOO_SHORT)
    }

    @Test
    fun `validate password shorter than 8 characters should return too short error`() {
        val result = validatePassword(INVALID_PASSWORD_SHORT)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.TOO_SHORT)
    }

    @Test
    fun `validate password without uppercase letter should return no uppercase error`() {
        val result = validatePassword(INVALID_PASSWORD_NO_UPPERCASE)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.NO_UPPERCASE_LETTER)
    }

    @Test
    fun `validate password without lowercase letter should return no lowercase error`() {
        val result = validatePassword(INVALID_PASSWORD_NO_LOWERCASE)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.NO_LOWERCASE_LETTER)
    }

    @Test
    fun `validate password without digit should return no digit error`() {
        val result = validatePassword(INVALID_PASSWORD_NO_DIGIT)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.NO_DIGIT)
    }

    @Test
    fun `validate password without special character should return no special character error`() {
        val result = validatePassword(INVALID_PASSWORD_NO_SPECIAL_CHAR)
        assertThat(result.error!!).containsAtLeast(PasswordValidationError.NO_SPECIAL_CHARACTER)
    }

    @Test
    fun `validate password with all requirements should succeed`() {
        val result = validatePassword(VALID_PASSWORD)
        assertThat(result.successfully).isTrue()
        assertThat(result.error).isNull()
    }

    private fun validatePassword(
        password: String,
    ): ValidationResult<List<PasswordValidationError>> = passwordValidator.validate(password)
}
