package com.jefisu.trackizer.auth.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.validation.EmailValidationError
import com.jefisu.trackizer.auth.domain.validation.PasswordValidationError
import com.jefisu.trackizer.core.util.Result
import com.jefisu.trackizer.testutil.EMPTY_EMAIL
import com.jefisu.trackizer.testutil.EMPTY_PASSWORD
import com.jefisu.trackizer.testutil.INVALID_EMAIL
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_NO_DIGIT
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_NO_LOWERCASE
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_NO_SPECIAL_CHAR
import com.jefisu.trackizer.testutil.INVALID_PASSWORD_NO_UPPERCASE
import com.jefisu.trackizer.testutil.VALID_EMAIL
import com.jefisu.trackizer.testutil.VALID_PASSWORD
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class RegisterUserUseCaseTest : BaseAuthUseCaseTest() {

    private lateinit var registerUserUseCase: RegisterUserUseCase

    override fun specificSetUp() {
        registerUserUseCase = RegisterUserUseCase(
            repository = fakeRepository,
            emailValidator = emailValidator,
            passwordValidator = passwordValidator,
        )
    }

    @Test
    fun `register with empty email should return validation error`() = runTest {
        val result = executeRegister(email = EMPTY_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(
            expectedEmailValidationError(EmailValidationError.CAN_T_BE_BLANK),
        )
    }

    @Test
    fun `register with invalid email format should return validation error`() = runTest {
        val result = executeRegister(email = INVALID_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(
            expectedEmailValidationError(EmailValidationError.INVALID_FORMAT),
        )
    }

    @Test
    fun `register with empty password should return validation error`() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = EMPTY_PASSWORD)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.TOO_SHORT),
        )
    }

    @Test
    fun `register with password without uppercase should return validation error`() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = INVALID_PASSWORD_NO_UPPERCASE)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.NO_UPPERCASE_LETTER),
        )
    }

    @Test
    fun `register with password without lowercase should return validation error`() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = INVALID_PASSWORD_NO_LOWERCASE)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.NO_LOWERCASE_LETTER),
        )
    }

    @Test
    fun `register with password without digit should return validation error`() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = INVALID_PASSWORD_NO_DIGIT)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.NO_DIGIT),
        )
    }

    @Test
    fun `register with password without special character should return validation error`() =
        runTest {
            val result =
                executeRegister(email = VALID_EMAIL, password = INVALID_PASSWORD_NO_SPECIAL_CHAR)

            assertThat(result).isEqualTo(
                expectedPasswordValidationError(PasswordValidationError.NO_SPECIAL_CHARACTER),
            )
        }

    @Test
    fun `register with valid credentials should succeed`() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(expectedSuccessResult())
    }

    @Test
    fun `register with existing email should return user already exists error`() = runTest {
        givenUserExists(email = VALID_EMAIL, password = VALID_PASSWORD)

        val result = executeRegister(email = VALID_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(expectedUserAlreadyExistsError())
    }

    private suspend fun executeRegister(
        email: String,
        password: String,
    ): Result<Unit, AuthMessage> = registerUserUseCase.execute(email, password)
}
