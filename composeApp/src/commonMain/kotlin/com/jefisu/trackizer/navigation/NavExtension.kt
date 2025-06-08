package com.jefisu.trackizer.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute

fun NavBackStackEntry.isCurrentDestination(destination: Destination) = this.destination.hasRoute(
    destination::class,
)
