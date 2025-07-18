package com.jefisu.trackizer.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jefisu.trackizer.feature.auth.di.rememberAuthScope
import com.jefisu.trackizer.feature.auth.presentation.login.LoginScreenRoot
import com.jefisu.trackizer.feature.auth.presentation.register.RegisterScreenRoot
import com.jefisu.trackizer.feature.auth.presentation.thirdpartyauth.ThirdPartyAuthRoot
import com.jefisu.trackizer.feature.welcome.WelcomeScreenRoot
import com.jefisu.trackizer.navigation.AnimationTarget
import com.jefisu.trackizer.navigation.AnimationType
import com.jefisu.trackizer.navigation.Destination
import com.jefisu.trackizer.navigation.animatedScreen
import com.jefisu.trackizer.navigation.navigateSingleTopTo
import com.jefisu.trackizer.navigation.sharedViewModel

fun NavGraphBuilder.authGraph(navController: NavController) {
    fun navigate(destination: Destination) {
        navController.navigateSingleTopTo<Destination.WelcomeScreen>(destination)
    }

    navigation<Destination.AuthGraph>(
        startDestination = Destination.WelcomeScreen,
    ) {
        animatedScreen<Destination.WelcomeScreen> {
            WelcomeScreenRoot(
                onNavigateToSignInScreen = {
                    navigate(Destination.LoginScreen)
                },
                onNavigateToSignUpScreen = {
                    navigate(Destination.ThirdPartyAuthScreen)
                },
            )
        }
        animatedScreen<Destination.ThirdPartyAuthScreen> {
            ThirdPartyAuthRoot(
                onNavigateToRegisterScreen = {
                    navigate(Destination.RegisterScreen)
                },
            )
        }
        animatedScreen<Destination.LoginScreen> {
            LoginScreenRoot(
                viewModel = sharedViewModel(navController, rememberAuthScope()),
                onNavigateToRegisterScreen = {
                    navigate(Destination.ThirdPartyAuthScreen)
                },
            )
        }
        animatedScreen<Destination.RegisterScreen> {
            RegisterScreenRoot(
                viewModel = sharedViewModel(navController, rememberAuthScope()),
                onNavigateToLoginScreen = {
                    navigate(Destination.LoginScreen)
                },
            )
        }
    }
}

val authNavAnimConfig: List<Pair<Destination, List<AnimationTarget>>> = listOf(
    Destination.WelcomeScreen to listOf(
        AnimationTarget(Destination.LoginScreen, AnimationType.BOTTOM_TO_TOP),
        AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.BOTTOM_TO_TOP),
        AnimationTarget(Destination.RegisterScreen, AnimationType.BOTTOM_TO_TOP),
    ),
    Destination.ThirdPartyAuthScreen to listOf(
        AnimationTarget(Destination.RegisterScreen, AnimationType.RIGHT_TO_LEFT),
        AnimationTarget(Destination.HomeScreen, AnimationType.BOTTOM_TO_TOP),
    ),
    Destination.LoginScreen to listOf(
        AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.TOP_TO_BOTTOM),
        AnimationTarget(Destination.HomeScreen, AnimationType.BOTTOM_TO_TOP),
    ),
    Destination.RegisterScreen to listOf(
        AnimationTarget(Destination.LoginScreen, AnimationType.LEFT_TO_RIGHT),
        AnimationTarget(Destination.ThirdPartyAuthScreen, AnimationType.TOP_TO_BOTTOM),
    ),
)
