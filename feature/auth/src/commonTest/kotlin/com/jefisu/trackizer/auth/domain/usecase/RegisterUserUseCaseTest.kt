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
    fun registerWithEmptyEmailShouldReturnValidationError() = runTest {
        val result = executeRegister(email = EMPTY_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(
            expectedEmailValidationError(EmailValidationError.CAN_T_BE_BLANK),
        )
    }

    @Test
    fun registerWithInvalidEmailFormatShouldReturnValidationError() = runTest {
        val result = executeRegister(email = INVALID_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(
            expectedEmailValidationError(EmailValidationError.INVALID_FORMAT),
        )
    }

    @Test
    fun registerWithEmptyPasswordShouldReturnValidationError() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = EMPTY_PASSWORD)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.TOO_SHORT),
        )
    }

    @Test
    fun registerWithPasswordWithoutUppercaseShouldReturnValidationError() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = INVALID_PASSWORD_NO_UPPERCASE)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.NO_UPPERCASE_LETTER),
        )
    }

    @Test
    fun registerWithPasswordWithoutLowercaseShouldReturnValidationError() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = INVALID_PASSWORD_NO_LOWERCASE)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.NO_LOWERCASE_LETTER),
        )
    }

    @Test
    fun registerWithPasswordWithoutDigitShouldReturnValidationError() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = INVALID_PASSWORD_NO_DIGIT)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.NO_DIGIT),
        )
    }

    @Test
    fun registerWithPasswordWithoutSpecialCharacterShouldReturnValidationError() = runTest {
        val result =
            executeRegister(email = VALID_EMAIL, password = INVALID_PASSWORD_NO_SPECIAL_CHAR)

        assertThat(result).isEqualTo(
            expectedPasswordValidationError(PasswordValidationError.NO_SPECIAL_CHARACTER),
        )
    }

    @Test
    fun registerWithValidCredentialsShouldSucceed() = runTest {
        val result = executeRegister(email = VALID_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(expectedSuccessResult())
    }

    @Test
    fun registerWithExistingEmailShouldReturnUserAlreadyExistsError() = runTest {
        givenUserExists(email = VALID_EMAIL, password = VALID_PASSWORD)

        val result = executeRegister(email = VALID_EMAIL, password = VALID_PASSWORD)

        assertThat(result).isEqualTo(expectedUserAlreadyExistsError())
    }

    private suspend fun executeRegister(
        email: String,
        password: String,
    ): Result<Unit, AuthMessage> = registerUserUseCase.execute(email, password)
}
