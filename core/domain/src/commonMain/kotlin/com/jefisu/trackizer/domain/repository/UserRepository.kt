package com.jefisu.trackizer.domain.repository

interface UserRepository {
    fun isLoggedIn(): Boolean
}
