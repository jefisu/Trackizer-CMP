package com.jefisu.trackizer.core.util

enum class Platform {
    IOS,
    ANDROID,
    DESKTOP,
}

expect fun getPlatform(): Platform
