package com.jefisu.trackizer.testutil.repository

import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.domain.repository.SubscriptionRepository
import kotlin.collections.plus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeSubscriptionRepository : SubscriptionRepository {

    private val _subscriptions = MutableStateFlow(emptyList<Subscription>())
    override val subscriptions = _subscriptions.asStateFlow()

    private var shouldReturnError = false

    fun setShouldReturnError() {
        shouldReturnError = true
    }

    override suspend fun addSubscription(subscription: Subscription): Result<Unit> {
        if (shouldReturnError) {
            return Result.failure(Exception("Error adding subscription"))
        }
        _subscriptions.update { it + subscription }
        return Result.success(Unit)
    }
}
