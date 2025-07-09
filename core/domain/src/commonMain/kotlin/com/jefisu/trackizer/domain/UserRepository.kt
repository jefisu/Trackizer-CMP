package com.jefisu.trackizer.domain

interface UserRepository {
    fun isLoggedIn(): Boolean
}
