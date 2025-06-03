package com.jefisu.trackizer

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Trackizer",
        state = rememberIphone16ProState()
    ) {
        App()
    }
}

@Composable
private fun rememberIphone16ProState() = rememberWindowState(
    size = DpSize(
        width = 393.dp,
        height = 852.dp
    )
)
