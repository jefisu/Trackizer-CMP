package com.jefisu.trackizer.auth.presentation.thirdpartyauth

import com.jefisu.trackizer.core.platform.auth.AuthProviderType

data class ThirdPartyAuthState(
    val providerLoading: AuthProviderType? = null,
)
