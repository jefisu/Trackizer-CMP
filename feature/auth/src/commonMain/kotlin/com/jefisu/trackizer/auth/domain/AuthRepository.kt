package com.jefisu.trackizer.auth.domain

import com.jefisu.trackizer.core.platform.auth.AuthProviderType
import com.jefisu.trackizer.core.util.Result

typealias EmptyAuthResult = Result<Unit, AuthMessage.Error>

interface AuthRepository {
    suspend fun logIn(email: String, password: String): EmptyAuthResult
    suspend fun register(email: String, password: String): EmptyAuthResult
    suspend fun resetPassword(email: String): Result<AuthMessage.Success, AuthMessage.Error>

    suspend fun signInProvider(providerType: AuthProviderType): Result<Unit, AuthMessage.Error>
}
