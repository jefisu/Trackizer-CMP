package com.jefisu.trackizer.core.platform.auth

import com.jefisu.trackizer.core.platform.ui.AndroidFacebookUIClient
import dev.gitlive.firebase.auth.FacebookAuthProvider
import dev.gitlive.firebase.auth.FirebaseAuth

class AndroidFacebookAuthProvider(
    private val facebookUIClient: AndroidFacebookUIClient,
    private val firebaseAuth: FirebaseAuth,
) : AuthProvider {

    override val type = AuthProviderType.FACEBOOK

    override suspend fun signIn() {
        facebookUIClient.logIn()

        val accessToken = facebookUIClient.getAccessToken()
        val credential = FacebookAuthProvider.credential(accessToken)
        firebaseAuth.signInWithCredential(credential)
    }
}
