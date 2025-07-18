package com.jefisu.trackizer.core.domain.model

import com.jefisu.trackizer.core.util.ImageData

data class SubscriptionService(
    val name: String,
    val imageData: ImageData,
    val id: String,
    val color: Long?,
)
