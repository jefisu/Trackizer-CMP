package com.jefisu.trackizer.core.domain.repository

import com.jefisu.trackizer.core.domain.model.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    val subscriptions: Flow<List<Subscription>>
    suspend fun addSubscription(subscription: Subscription): Result<Unit>
}
