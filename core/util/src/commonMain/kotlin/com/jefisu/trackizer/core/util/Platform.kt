package com.jefisu.trackizer.core.util

enum class Platform {
    IOS,
    ANDROID,
    DESKTOP,
}

expect fun getPlatform(): Platform

inline fun runOnPlatform(
    ios: () -> Unit = {},
    android: () -> Unit = {},
    desktop: () -> Unit = {},
) {
    when (getPlatform()) {
        Platform.IOS -> ios()
        Platform.ANDROID -> android()
        Platform.DESKTOP -> desktop()
    }
}
