package com.jefisu.trackizer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jefisu.trackizer.welcome.WelcomeScreenRoot

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destination.WelcomeScreen,
    ) {
        composable<Destination.WelcomeScreen> {
            WelcomeScreenRoot(
                onNavigateToSignInScreen = {},
                onNavigateToSignUpScreen = {},
            )
        }
    }
}
