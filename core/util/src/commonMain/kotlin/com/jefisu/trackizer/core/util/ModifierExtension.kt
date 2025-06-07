package com.jefisu.trackizer.core.util

import androidx.compose.ui.Modifier

fun Modifier.applyPlatformSpecific(
    ios: Modifier.() -> Modifier = { this },
    android: Modifier.() -> Modifier = { this },
    desktop: Modifier.() -> Modifier = { this },
): Modifier {
    return when (getPlatform()) {
        Platform.IOS -> ios()
        Platform.ANDROID -> android()
        Platform.DESKTOP -> desktop()
    }
}
