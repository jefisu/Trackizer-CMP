package com.jefisu.trackizer.feature.auth.domain.usecase

import com.jefisu.trackizer.core.util.Result
import com.jefisu.trackizer.feature.auth.di.AuthScope
import com.jefisu.trackizer.feature.auth.domain.AuthMessage
import com.jefisu.trackizer.feature.auth.domain.AuthRepository
import com.jefisu.trackizer.feature.auth.domain.validation.EmailValidator
import org.koin.core.annotation.Scope

@Scope(AuthScope::class)
class LoginUserUseCase(
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
