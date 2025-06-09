package com.jefisu.trackizer.auth.presentation.thirdpartyauth

import androidx.lifecycle.ViewModel

class ThirdPartyAuthViewModel : ViewModel() {

    fun onAction(action: ThirdPartyAuthAction) {
        when (action) {
            ThirdPartyAuthAction.OnGoogleClick -> signInWithGoogle()
            ThirdPartyAuthAction.OnFacebookClick -> signInWithFacebook()
            else -> Unit
        }
    }

    private fun signInWithFacebook() {
    }

    private fun signInWithGoogle() {
    }
}
