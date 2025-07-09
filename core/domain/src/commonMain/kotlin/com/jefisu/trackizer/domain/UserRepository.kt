package com.jefisu.trackizer.domain

interface UserRepository {
    fun isAuthenticated(): Boolean
}
