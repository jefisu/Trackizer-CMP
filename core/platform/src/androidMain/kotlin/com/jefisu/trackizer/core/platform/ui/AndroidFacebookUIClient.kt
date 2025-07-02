package com.jefisu.trackizer.core.platform.ui

import android.app.Activity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.suspendCancellableCoroutine

typealias AccessToken = String

class AndroidFacebookUIClient(
    private val activity: Activity,
) {

    val loginManager = LoginManager.getInstance()
    val callbackManager = CallbackManager.Factory.create()

    suspend fun getAccessToken(): AccessToken = suspendCancellableCoroutine { continuation ->
        loginManager.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    continuation.resumeWithException(Exception("Login cancelled by user"))
                }

                override fun onError(error: FacebookException) {
                    continuation.resumeWithException(error)
                }

                override fun onSuccess(result: LoginResult) {
                    val accessToken = result.accessToken.token
                    continuation.resume(accessToken)
                }
            },
        )
    }

    fun logIn() {
        loginManager.logInWithReadPermissions(
            activity,
            listOf("email", "public_profile"),
        )
    }
}
