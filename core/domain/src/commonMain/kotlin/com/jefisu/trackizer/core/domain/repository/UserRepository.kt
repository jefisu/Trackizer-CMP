package com.jefisu.trackizer.core.domain.repository

interface UserRepository {
    fun isLoggedIn(): Boolean
}
