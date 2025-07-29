package com.jefisu.trackizer.testutil

import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.domain.model.SubscriptionService
import com.jefisu.trackizer.core.util.ImageData
import com.jefisu.trackizer.core.util.nowUtc

val subServices = listOf(
    SubscriptionService(
        name = "Netflix",
        imageData = ImageData.Server(""),
        id = "1",
        color = null,
    ),
    SubscriptionService(
        name = "Spotify",
        imageData = ImageData.Server(""),
        id = "2",
        color = null,
    ),
)

val validSubscription = Subscription(
    id = "1",
    service = subServices[0],
    price = 9.99f,
    firstPayment = nowUtc(),
    reminder = false,
    description = "Some description",
)

val invalidPriceSubscription = validSubscription.copy(price = -1.0f)
