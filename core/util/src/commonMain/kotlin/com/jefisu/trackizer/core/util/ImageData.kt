package com.jefisu.trackizer.core.util

import org.jetbrains.compose.resources.DrawableResource

sealed interface ImageData {
    data class Device(val resource: DrawableResource) : ImageData
    data class Server(val url: String) : ImageData
}
