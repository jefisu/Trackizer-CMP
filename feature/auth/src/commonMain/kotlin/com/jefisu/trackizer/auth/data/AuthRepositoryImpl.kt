package com.jefisu.trackizer.auth.data

import com.jefisu.trackizer.auth.domain.AuthMessage
import com.jefisu.trackizer.auth.domain.AuthRepository
import com.jefisu.trackizer.auth.domain.EmptyAuthResult
import com.jefisu.trackizer.core.util.Result
import dev.gitlive.firebase.FirebaseException
import dev.gitlive.firebase.FirebaseNetworkException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidUserException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import dev.gitlive.firebase.auth.auth
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.ensureActive
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class AuthRepositoryImpl(
    @Provided private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override suspend fun logIn(email: String, password: String): EmptyAuthResult {
        val result = runCatching { firebaseAuth.signInWithEmailAndPassword(email, password) }
        return result.fold(
            onSuccess = { Result.Success(Unit) },
            onFailure = {
                coroutineContext.ensureActive()
                Result.Error(it.toAuthMessage())
            },
        )
    }

    override suspend fun register(email: String, password: String): EmptyAuthResult {
        val result = runCatching { firebaseAuth.createUserWithEmailAndPassword(email, password) }
        return result.fold(
            onSuccess = { Result.Success(Unit) },
            onFailure = {
                coroutineContext.ensureActive()
                Result.Error(it.toAuthMessage())
            },
        )
    }

    override suspend fun resetPassword(
        email: String,
    ): Result<AuthMessage.Success, AuthMessage.Error> {
        val result = runCatching { firebaseAuth.sendPasswordResetEmail(email) }
        return result.fold(
            onSuccess = {
                Result.Success(AuthMessage.Success.PasswordResetEmailSent)
            },
            onFailure = {
                coroutineContext.ensureActive()
                Result.Error(it.toAuthMessage())
            },
        )
    }

    private fun Throwable.toAuthMessage(): AuthMessage.Error {
        return when (this) {
            is FirebaseAuthInvalidCredentialsException -> AuthMessage.Error.InvalidCredentials
            is FirebaseAuthUserCollisionException -> AuthMessage.Error.UserAlreadyExists
            is FirebaseAuthInvalidUserException -> AuthMessage.Error.UserNotFound
            is FirebaseNetworkException -> AuthMessage.Error.InternetUnavailable
            is FirebaseException -> AuthMessage.Error.ServerError
            else -> AuthMessage.Error.ServerError
        }
    }
}
