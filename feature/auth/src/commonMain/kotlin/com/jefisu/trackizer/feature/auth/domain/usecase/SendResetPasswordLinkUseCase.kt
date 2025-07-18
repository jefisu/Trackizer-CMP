package com.jefisu.trackizer.feature.auth.domain.usecase

import com.jefisu.trackizer.core.util.Result
import com.jefisu.trackizer.feature.auth.di.AuthScope
import com.jefisu.trackizer.feature.auth.domain.AuthMessage
import com.jefisu.trackizer.feature.auth.domain.AuthRepository
import com.jefisu.trackizer.feature.auth.domain.validation.EmailValidator
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
