package com.jefisu.trackizer.core.platform.auth

interface AuthProvider {
    val type: AuthProviderType
    suspend fun signIn()
}
