package com.jefisu.trackizer.domain.model

import kotlinx.datetime.LocalDateTime

data class Subscription(
    val serviceType: SubscriptionServiceType,
    val description: String,
    val price: Float,
    val firstPayment: LocalDateTime,
    val reminder: Boolean,
    val id: String,
)
