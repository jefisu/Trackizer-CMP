package com.jefisu.trackizer.auth.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.validation.EmailValidationError
import com.jefisu.trackizer.core.util.Result
import com.jefisu.trackizer.testutil.EMPTY_EMAIL
import com.jefisu.trackizer.testutil.EMPTY_PASSWORD
import com.jefisu.trackizer.testutil.INVALID_EMAIL
import com.jefisu.trackizer.testutil.VALID_EMAIL
import com.jefisu.trackizer.testutil.VALID_PASSWORD
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class LoginUserUseCaseTest : BaseAuthUseCaseTest() {

    private lateinit var loginUserUseCase: LoginUserUseCase

    override fun specificSetUp() {
        loginUserUseCase = LoginUserUseCase(
            repository = fakeRepository,
            emailValidator = emailValidator,
        )
    }

    @Test
    fun `login with empty email should return validation error`() = runTest {
        val result = executeLogin(email = EMPTY_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(
            expectedEmailValidationError(EmailValidationError.CAN_T_BE_BLANK),
        )
    }

    @Test
    fun `login with invalid email format should return validation error`() = runTest {
        val result = executeLogin(email = INVALID_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(
            expectedEmailValidationError(EmailValidationError.INVALID_FORMAT),
        )
    }

    @Test
    fun `login with empty password should return invalid credentials error`() = runTest {
        val result = executeLogin(email = VALID_EMAIL, password = EMPTY_PASSWORD)

        assertThat(result).isEqualTo(expectedInvalidCredentialsError())
    }

    @Test
    fun `login with valid credentials for existing user should succeed`() = runTest {
        givenUserExists(email = VALID_EMAIL, password = VALID_PASSWORD)

        val result = executeLogin(email = VALID_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(expectedSuccessResult())
    }

    private suspend fun executeLogin(email: String, password: String): Result<Unit, AuthMessage> =
        loginUserUseCase.execute(email, password)
}
