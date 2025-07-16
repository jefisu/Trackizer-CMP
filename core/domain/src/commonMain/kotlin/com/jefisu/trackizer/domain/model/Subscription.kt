package com.jefisu.trackizer.domain.model

import kotlinx.datetime.LocalDateTime

data class Subscription(
    val service: SubscriptionService,
    val description: String,
    val price: Float,
    val firstPayment: LocalDateTime,
    val reminder: Boolean,
    val id: String,
)
