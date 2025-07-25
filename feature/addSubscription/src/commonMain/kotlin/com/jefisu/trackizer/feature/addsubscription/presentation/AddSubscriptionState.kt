package com.jefisu.trackizer.feature.addsubscription.presentation

import com.jefisu.trackizer.core.domain.model.SubscriptionService

data class AddSubscriptionState(
    val price: String = "",
    val description: String = "",
    val service: SubscriptionService? = null,
    val servicesAvailable: List<SubscriptionService> = emptyList(),
)
