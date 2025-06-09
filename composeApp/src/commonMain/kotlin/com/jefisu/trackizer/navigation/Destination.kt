package com.jefisu.trackizer.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object AuthGraph : Destination

    @Serializable
    data object WelcomeScreen : Destination

    @Serializable
    data object ThirdPartyAuthScreen : Destination

    @Serializable
    data object LoginScreen : Destination
}
