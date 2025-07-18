package com.jefisu.trackizer.data.repository

import com.jefisu.trackizer.domain.repository.UserRepository
import dev.gitlive.firebase.auth.FirebaseAuth
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class UserRepositoryImpl(
    @Provided private val firebaseAuth: FirebaseAuth,
) : UserRepository {
    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
