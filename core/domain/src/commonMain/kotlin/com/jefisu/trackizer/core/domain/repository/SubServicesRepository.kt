package com.jefisu.trackizer.core.domain.repository

import com.jefisu.trackizer.core.domain.model.SubscriptionService
import kotlinx.coroutines.flow.Flow

interface SubServicesRepository {
    val services: Flow<List<SubscriptionService>>
}
