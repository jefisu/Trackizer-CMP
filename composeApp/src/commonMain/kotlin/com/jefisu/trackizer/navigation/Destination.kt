package com.jefisu.trackizer.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object WelcomeScreen : Destination
}
