package com.jefisu.trackizer.auth.domain

import com.jefisu.trackizer.core.util.Result

typealias EmptyAuthResult = Result<Unit, AuthMessage.Error>

interface AuthRepository {
    suspend fun logIn(email: String, password: String): EmptyAuthResult
}
