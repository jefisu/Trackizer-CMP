package com.jefisu.trackizer.feature.auth.presentation.thirdpartyauth

import com.jefisu.trackizer.core.platform.auth.AuthProviderType

sealed interface ThirdPartyAuthAction {
    data object OnSignUpEmailClick : ThirdPartyAuthAction

    data class OnProviderClick(val type: AuthProviderType) : ThirdPartyAuthAction
}
