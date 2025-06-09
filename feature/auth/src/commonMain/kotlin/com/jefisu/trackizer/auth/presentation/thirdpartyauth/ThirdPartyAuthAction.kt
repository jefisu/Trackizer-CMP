package com.jefisu.trackizer.auth.presentation.thirdpartyauth

sealed interface ThirdPartyAuthAction {
    data object OnGoogleClick : ThirdPartyAuthAction
    data object OnFacebookClick : ThirdPartyAuthAction
    data object OnSignUpEmailClick : ThirdPartyAuthAction
}
