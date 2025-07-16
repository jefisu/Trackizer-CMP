package com.jefisu.trackizer.domain.repository

import com.jefisu.trackizer.domain.model.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    val subscriptions: Flow<List<Subscription>>
}
