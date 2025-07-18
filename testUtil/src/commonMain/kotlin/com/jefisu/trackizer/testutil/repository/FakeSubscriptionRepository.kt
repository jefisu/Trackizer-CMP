package com.jefisu.trackizer.testutil.repository

import com.jefisu.trackizer.domain.model.Subscription
import com.jefisu.trackizer.domain.repository.SubscriptionRepository
import kotlin.collections.plus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeSubscriptionRepository : SubscriptionRepository {

    private val _subscriptions = MutableStateFlow(emptyList<Subscription>())
    override val subscriptions = _subscriptions.asStateFlow()

    override suspend fun addSubscription(subscription: Subscription) {
        _subscriptions.update { it + subscription }
    }
}
