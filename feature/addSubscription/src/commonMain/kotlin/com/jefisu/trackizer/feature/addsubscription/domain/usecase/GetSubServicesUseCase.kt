package com.jefisu.trackizer.feature.addsubscription.domain.usecase

import com.jefisu.trackizer.core.domain.model.SubscriptionService
import com.jefisu.trackizer.core.domain.repository.SubServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEmpty
import org.koin.core.annotation.Single

@Single
class GetSubServicesUseCase(
    private val repository: SubServicesRepository,
) {
    fun execute(): Flow<List<SubscriptionService>> {
        return repository.services
            .onEmpty { emptyList<SubscriptionService>() }
    }
}
