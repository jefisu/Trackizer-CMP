package com.jefisu.trackizer.auth.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.validation.EmailValidationError
import com.jefisu.trackizer.core.util.Result
import com.jefisu.trackizer.testutil.EMPTY_EMAIL
import com.jefisu.trackizer.testutil.INVALID_EMAIL
import com.jefisu.trackizer.testutil.VALID_EMAIL
import com.jefisu.trackizer.testutil.VALID_PASSWORD
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class SendResetPasswordLinkUseCaseTest : BaseAuthUseCaseTest() {

    private lateinit var sendResetPasswordLinkUseCase: SendResetPasswordLinkUseCase

    override fun specificSetUp() {
        sendResetPasswordLinkUseCase = SendResetPasswordLinkUseCase(
            repository = fakeRepository,
            emailValidator = emailValidator,
        )
    }

    @Test
    fun `send reset password link with empty email should return validation error`() = runTest {
        val result = executeSendResetPasswordLink(EMPTY_EMAIL)

        assertThat(result).isEqualTo(
            expectedEmailValidationError(EmailValidationError.CAN_T_BE_BLANK),
        )
    }

    @Test
    fun `send reset password link with invalid email format should return validation error`() =
        runTest {
            val result = executeSendResetPasswordLink(INVALID_EMAIL)

            assertThat(result).isEqualTo(
                expectedEmailValidationError(EmailValidationError.INVALID_FORMAT),
            )
        }

    @Test
    fun `send reset password link for existing user should succeed`() = runTest {
        givenUserExists(email = VALID_EMAIL, password = VALID_PASSWORD)

        val result = executeSendResetPasswordLink(VALID_EMAIL)

        assertThat(result).isInstanceOf(Result.Success::class)
    }

    @Test
    fun `send reset password link for non-existing user should return user not found error`() =
        runTest {
            val result = executeSendResetPasswordLink(VALID_EMAIL)

            assertThat(result).isEqualTo(expectedUserNotFoundError())
        }

    private suspend fun executeSendResetPasswordLink(
        email: String,
    ): Result<AuthMessage.Success, AuthMessage> = sendResetPasswordLinkUseCase.execute(email)
}
