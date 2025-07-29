package com.jefisu.trackizer.testutil.repository

import com.jefisu.trackizer.core.domain.model.SubscriptionService
import com.jefisu.trackizer.core.domain.repository.SubServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSubServicesRepository : SubServicesRepository {

    private val servicesToReturn = mutableListOf<SubscriptionService>()

    fun setServices(services: List<SubscriptionService>) {
        servicesToReturn.addAll(services)
    }

    override val services: Flow<List<SubscriptionService>>
        get() = flowOf(servicesToReturn)
}
