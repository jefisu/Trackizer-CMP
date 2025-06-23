package com.jefisu.trackizer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.jefisu.trackizer.auth.di.rememberAuthScope
import com.jefisu.trackizer.auth.presentation.util.AuthEvent
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.FlashMessageDialog
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.MessageManager
import com.jefisu.trackizer.core.ui.ObserveAsEvents
import com.jefisu.trackizer.domain.UserRepository
import com.jefisu.trackizer.navigation.Destination
import com.jefisu.trackizer.navigation.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() = TrackizerTheme {
    val navController = rememberNavController()
    val message by MessageManager.message.collectAsStateWithLifecycle()
    val userRepository = koinInject<UserRepository>()
    val authScope = rememberAuthScope()

    FlashMessageDialog(
        message = message,
        onDismiss = MessageManager::closeMessage,
    )

    ObserveAsEvents(EventManager.events) { event ->
        if (event !is AuthEvent.UserAuthenticated) return@ObserveAsEvents
        navController.navigate(Destination.AuthenticatedGraph) {
            popUpTo<Destination.AuthGraph> { inclusive = true }
        }
        if (authScope.isNotClosed()) authScope.close()
    }

    NavGraph(
        startDestination = when {
            userRepository.isAuthenticated() -> {
                authScope.close()
                Destination.AuthenticatedGraph
            }

            else -> Destination.AuthGraph
        },
        navController = navController,
    )
}
