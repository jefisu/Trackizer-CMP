package com.jefisu.trackizer.core.platform.auth

import cocoapods.GoogleSignIn.GIDGoogleUser
import cocoapods.GoogleSignIn.GIDSignIn
import com.jefisu.trackizer.core.util.toException
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.UIKit.UIApplication

class IOSGoogleAuthProvider(
    private val firebaseAuth: FirebaseAuth,
) : AuthProvider {

    override val type = AuthProviderType.GOOGLE

    override suspend fun signIn() {
        val googleUser = signInWithGoogle()
        val credential = GoogleAuthProvider.credential(
            idToken = googleUser.idToken?.tokenString,
            accessToken = googleUser.accessToken.tokenString,
        )
        firebaseAuth.signInWithCredential(credential)
    }

    private suspend fun signInWithGoogle(): GIDGoogleUser {
        return suspendCancellableCoroutine coroutine@{ continuation ->
            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
                ?: run {
                    continuation.resumeWithException(
                        IllegalArgumentException("Root view controller is null"),
                    )
                    return@coroutine
                }

            GIDSignIn.sharedInstance.signInWithPresentingViewController(
                presentingViewController = rootViewController,
            ) signInScope@{ result, nsError ->
                result?.user?.let { googleUser ->
                    continuation.resume(googleUser)
                    return@signInScope
                }

                continuation.resumeWithException(
                    nsError?.toException() ?: IllegalArgumentException("Google User is null"),
                )
            }
        }
    }
}
