package com.jefisu.trackizer.auth.data

import com.jefisu.trackizer.core.platform.auth.AuthProviderType
import com.jefisu.trackizer.core.util.Result
import com.jefisu.trackizer.feature.auth.domain.AuthMessage
import com.jefisu.trackizer.feature.auth.domain.AuthRepository
import com.jefisu.trackizer.feature.auth.domain.EmptyAuthResult

class FakeAuthRepository : AuthRepository {

    private val users = mutableMapOf<String, String>()

    override suspend fun logIn(email: String, password: String): EmptyAuthResult {
        if (!users.containsKey(email)) {
            return Result.Error(AuthMessage.Error.UserNotFound)
        }
        return Result.Success(Unit)
    }

    override suspend fun register(email: String, password: String): EmptyAuthResult {
        if (users.containsKey(email)) {
            return Result.Error(AuthMessage.Error.UserAlreadyExists)
        }
        users[email] = password
        return Result.Success(Unit)
    }

    override suspend fun resetPassword(
        email: String,
    ): Result<AuthMessage.Success, AuthMessage.Error> {
        if (!users.containsKey(email)) {
            return Result.Error(AuthMessage.Error.UserNotFound)
        }
        return Result.Success(AuthMessage.Success.PasswordResetEmailSent)
    }

    override suspend fun signInProvider(
        providerType: AuthProviderType,
    ): Result<Unit, AuthMessage.Error> {
        return Result.Success(Unit)
    }
}
