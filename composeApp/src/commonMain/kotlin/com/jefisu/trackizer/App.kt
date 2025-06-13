package com.jefisu.trackizer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.FlashMessageDialog
import com.jefisu.trackizer.core.ui.MessageManager
import com.jefisu.trackizer.di.AppModule
import com.jefisu.trackizer.domain.UserRepository
import com.jefisu.trackizer.navigation.Destination
import com.jefisu.trackizer.navigation.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.ksp.generated.module

@Composable
@Preview
fun App() = TrackizerTheme {
    val navController = rememberNavController()
    val message by MessageManager.message.collectAsStateWithLifecycle()

    FlashMessageDialog(
        message = message,
        onDismiss = MessageManager::closeMessage,
    )

    KoinApplication(
        application = {
            modules(AppModule().module)
        },
    ) {
        val userRepository = koinInject<UserRepository>()
        NavGraph(
            startDestination = when {
                userRepository.isAuthenticated() -> Destination.AuthenticatedGraph
                else -> Destination.AuthGraph
            },
            navController = navController,
        )
    }
}
