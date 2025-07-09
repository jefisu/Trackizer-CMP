package com.jefisu.trackizer.auth.domain.usecase

import com.jefisu.trackizer.auth.data.FakeAuthRepository
import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.validation.EmailValidationError
import com.jefisu.trackizer.auth.domain.validation.EmailValidator
import com.jefisu.trackizer.auth.domain.validation.PasswordValidationError
import com.jefisu.trackizer.auth.domain.validation.PasswordValidator
import com.jefisu.trackizer.core.util.Result
import kotlin.test.BeforeTest

/**
 * Base class for auth use case tests providing common setup and helper methods
 */
abstract class BaseAuthUseCaseTest {

    protected lateinit var fakeRepository: FakeAuthRepository
    protected lateinit var emailValidator: EmailValidator
    protected lateinit var passwordValidator: PasswordValidator

    @BeforeTest
    fun baseSetUp() {
        fakeRepository = FakeAuthRepository()
        emailValidator = EmailValidator()
        passwordValidator = PasswordValidator()
        specificSetUp()
    }

    /**
     * Override this method to provide specific setup for each test class
     */
    protected abstract fun specificSetUp()

    protected fun expectedEmailValidationError(
        error: EmailValidationError,
    ): Result<Unit, AuthMessage> = Result.Error(AuthMessage.Error.Validation.Email(error))

    protected fun expectedPasswordValidationError(
        error: PasswordValidationError,
    ): Result<Unit, AuthMessage> = Result.Error(AuthMessage.Error.Validation.Password(error))

    protected fun expectedInvalidCredentialsError(): Result<Unit, AuthMessage> =
        Result.Error(AuthMessage.Error.InvalidCredentials)

    protected fun expectedUserAlreadyExistsError(): Result<Unit, AuthMessage> =
        Result.Error(AuthMessage.Error.UserAlreadyExists)

    protected fun expectedUserNotFoundError(): Result<Unit, AuthMessage> =
        Result.Error(AuthMessage.Error.UserNotFound)

    protected fun expectedSuccessResult(): Result<Unit, AuthMessage> = Result.Success(Unit)

    protected suspend fun givenUserExists(email: String, password: String) {
        fakeRepository.register(email, password)
    }
}
