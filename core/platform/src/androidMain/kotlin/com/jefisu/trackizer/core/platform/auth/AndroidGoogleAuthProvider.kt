package com.jefisu.trackizer.core.platform.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jefisu.trackizer.core.platform.BuildKonfig
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.GoogleAuthProvider

class AndroidGoogleAuthProvider(
    private val context: Context,
    private val firebaseAuth: FirebaseAuth,
) : AuthProvider {

    private val credentialManager = CredentialManager.create(context)

    override val type = AuthProviderType.GOOGLE

    override suspend fun signIn() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildKonfig.GOOGLE_CLIENT_ID)
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val credentialResponse = credentialManager.getCredential(context, request)

        val credential = credentialResponse.credential as? CustomCredential
            ?: error("Unexpected credential type")

        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

        val googleFirebaseCredential = GoogleAuthProvider.credential(
            idToken = googleIdTokenCredential.idToken,
            accessToken = null,
        )
        firebaseAuth.signInWithCredential(googleFirebaseCredential)
    }
}
