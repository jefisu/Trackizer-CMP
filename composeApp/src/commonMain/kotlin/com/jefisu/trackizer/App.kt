package com.jefisu.trackizer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.navigation.NavGraph
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() = TrackizerTheme {
    val navController = rememberNavController()
    NavGraph(navController)
}
