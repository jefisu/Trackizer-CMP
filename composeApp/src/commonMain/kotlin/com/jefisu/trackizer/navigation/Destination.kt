package com.jefisu.trackizer.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    data object AuthGraph : Destination

    @Serializable
    data object LoggedGraph : Destination

    @Serializable
    data object WelcomeScreen : Destination

    @Serializable
    data object ThirdPartyAuthScreen : Destination

    @Serializable
    data object LoginScreen : Destination

    @Serializable
    data object RegisterScreen : Destination

    @Serializable
    data object HomeScreen : Destination

    @Serializable
    data object SpendingBudgetsScreen : Destination

    @Serializable
    data object AddSubscriptionScreen: Destination

    @Serializable
    data object CalendarScreen: Destination

    @Serializable
    data object CreditCardsScreen: Destination
}
