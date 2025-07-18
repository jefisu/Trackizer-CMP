package com.jefisu.trackizer.core.data.remote.mapper

import com.jefisu.trackizer.core.data.remote.model.SubscriptionServiceRemote
import com.jefisu.trackizer.core.domain.model.SubscriptionService
import com.jefisu.trackizer.core.util.ImageData

fun SubscriptionServiceRemote.toSubscriptionService(): SubscriptionService {
    return SubscriptionService(
        name = name,
        imageData = ImageData.Server(imageUrl),
        id = id,
        color = color,
    )
}
