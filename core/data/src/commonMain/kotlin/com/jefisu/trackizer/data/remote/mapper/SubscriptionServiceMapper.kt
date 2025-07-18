package com.jefisu.trackizer.data.remote.mapper

import com.jefisu.trackizer.core.util.ImageData
import com.jefisu.trackizer.data.remote.model.SubscriptionServiceRemote
import com.jefisu.trackizer.domain.model.SubscriptionService

fun SubscriptionServiceRemote.toSubscriptionService(): SubscriptionService {
    return SubscriptionService(
        name = name,
        imageData = ImageData.Server(imageUrl),
        id = id,
        color = color,
    )
}
