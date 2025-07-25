package com.jefisu.trackizer.feature.addsubscription.presentation

import com.jefisu.trackizer.core.domain.model.SubscriptionService

sealed interface AddSubscriptionAction {
    data class UpdatePrice(val price: String) : AddSubscriptionAction
    data class UpdateDescription(val description: String) : AddSubscriptionAction
    data class UpdateSubscriptionService(val service: SubscriptionService) : AddSubscriptionAction
    data object AddSubscription : AddSubscriptionAction
    data object OnBackClick : AddSubscriptionAction
}
