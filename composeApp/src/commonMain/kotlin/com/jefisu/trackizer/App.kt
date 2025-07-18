@file:OptIn(KoinExperimentalAPI::class)

package com.jefisu.trackizer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import coil3.compose.setSingletonImageLoaderFactory
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.FlashMessageDialog
import com.jefisu.trackizer.core.domain.repository.UserRepository
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.ObserveAsEvents
import com.jefisu.trackizer.core.util.closeKoinScope
import com.jefisu.trackizer.di.AppModule
import com.jefisu.trackizer.di.nativeModule
import com.jefisu.trackizer.feature.auth.di.AUTH_SCOPE_ID
import com.jefisu.trackizer.feature.auth.presentation.util.AuthEvent
import com.jefisu.trackizer.navigation.Destination
import com.jefisu.trackizer.navigation.NavGraph
import com.jefisu.trackizer.util.CoilConfig
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration
import org.koin.ksp.generated.module

@Composable
@Preview
fun App(configure: (() -> Unit)? = null) = TrackizerTheme {
    val navController = rememberNavController()

    setSingletonImageLoaderFactory { context ->
        CoilConfig.buildImageLoader(context)
    }

    KoinMultiplatformApplication(
        config = KoinConfiguration {
            modules(AppModule().module, nativeModule)
        },
    ) {
        LaunchedEffect(Unit) {
            configure?.invoke()
        }

        val viewModel = koinViewModel<AppViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val userRepository = koinInject<UserRepository>()

        FlashMessageDialog(
            message = state.message,
            onDismiss = viewModel::dismissMessage,
        )

        ObserveAsEvents(EventManager.events) { event ->
            if (event !is AuthEvent.UserAuthenticated) return@ObserveAsEvents
            navController.navigate(Destination.LoggedGraph) {
                popUpTo<Destination.AuthGraph> { inclusive = true }
            }
            closeKoinScope(AUTH_SCOPE_ID)
        }

        NavGraph(
            startDestination = when {
                userRepository.isLoggedIn() -> Destination.LoggedGraph
                else -> Destination.AuthGraph
            },
            navController = navController,
        )
    }
}
