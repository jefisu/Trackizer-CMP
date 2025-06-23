package com.jefisu.trackizer.auth.domain.usecase

import com.jefisu.trackizer.auth.di.AuthScope
import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.AuthRepository
import com.jefisu.trackizer.auth.domain.validation.EmailValidator
import com.jefisu.trackizer.core.util.Result
import org.koin.core.annotation.Scope

@Scope(AuthScope::class)
class SendResetPasswordLinkUseCase(
    private val repository: AuthRepository,
    private val emailValidator: EmailValidator,
) {
    suspend fun execute(email: String): Result<AuthMessage.Success, AuthMessage.Error> {
        emailValidator
            .validate(email)
            .takeIf { !it.successfully }
            ?.let { return Result.Error(AuthMessage.Error.Validation.Email(it.error!!)) }

        return repository.resetPassword(email)
    }
}
