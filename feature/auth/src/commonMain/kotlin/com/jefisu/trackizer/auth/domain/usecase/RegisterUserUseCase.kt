package com.jefisu.trackizer.auth.domain.usecase

import com.jefisu.trackizer.auth.di.AuthScope
import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.AuthRepository
import com.jefisu.trackizer.auth.domain.validation.EmailValidator
import com.jefisu.trackizer.auth.domain.validation.PasswordValidator
import com.jefisu.trackizer.core.util.Result
import org.koin.core.annotation.Scope

@Scope(AuthScope::class)
class RegisterUserUseCase(
    private val repository: AuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
) {
    suspend fun execute(email: String, password: String): Result<Unit, AuthMessage> {
        val errors = mutableListOf<AuthMessage>()

        emailValidator
            .validate(email)
            .takeIf { !it.successfully }
            ?.let { errors.add(AuthMessage.Error.Validation.Email(it.error!!)) }

        passwordValidator
            .validate(password)
            .takeIf { !it.successfully }
            ?.let { errors.add(AuthMessage.Error.Validation.Password(it.error!!.first())) }

        if (errors.isNotEmpty()) return Result.Error(errors.first())

        return repository.register(email, password)
    }
}
