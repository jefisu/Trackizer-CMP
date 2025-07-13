package com.jefisu.trackizer.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jefisu.trackizer.home.HomeScreenRoot
import com.jefisu.trackizer.navigation.AnimationTarget
import com.jefisu.trackizer.navigation.Destination
import com.jefisu.trackizer.navigation.animatedScreen

fun NavGraphBuilder.loggedGraph() {
    navigation<Destination.LoggedGraph>(
        startDestination = Destination.HomeScreen,
    ) {
        animatedScreen<Destination.HomeScreen> {
            HomeScreenRoot()
        }
    }
}

val loggedNavAnimConfig: List<Pair<Destination, List<AnimationTarget>>> = listOf()
