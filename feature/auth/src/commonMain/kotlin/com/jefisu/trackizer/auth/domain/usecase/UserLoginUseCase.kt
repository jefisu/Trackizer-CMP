package com.jefisu.trackizer.auth.domain.usecase

import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.AuthRepository
import com.jefisu.trackizer.auth.domain.validation.EmailValidator
import com.jefisu.trackizer.core.util.Result
import org.koin.core.annotation.Single

@Single
class UserLoginUseCase(
    private val repository: AuthRepository,
    private val emailValidator: EmailValidator,
) {
    suspend fun execute(email: String, password: String): Result<Unit, AuthMessage> {
        val errors = mutableListOf<AuthMessage>()

        emailValidator
            .validate(email)
            .takeIf { !it.successfully }
            ?.let { errors.add(AuthMessage.Error.Validation.Email(it.error!!)) }

        if (password.isEmpty()) errors.add(AuthMessage.Error.InvalidCredentials)

        if (errors.isNotEmpty()) return Result.Error(errors.first())

        return repository.logIn(email, password)
    }
}
