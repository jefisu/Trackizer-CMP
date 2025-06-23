package com.jefisu.trackizer.auth.presentation.thirdpartyauth

import com.jefisu.trackizer.core.platform.auth.AuthProviderType

sealed interface ThirdPartyAuthAction {
    data object OnSignUpEmailClick : ThirdPartyAuthAction

    data class OnProviderClick(val type: AuthProviderType) : ThirdPartyAuthAction
}
