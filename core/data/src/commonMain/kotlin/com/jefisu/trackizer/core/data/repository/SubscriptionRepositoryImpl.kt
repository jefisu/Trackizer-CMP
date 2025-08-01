@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jefisu.trackizer.core.data.repository

import com.jefisu.trackizer.core.data.di.RemoteDataSourceQualifiers
import com.jefisu.trackizer.core.data.remote.RemoteDataSource
import com.jefisu.trackizer.core.data.remote.mapper.toSubscription
import com.jefisu.trackizer.core.data.remote.mapper.toSubscriptionRemote
import com.jefisu.trackizer.core.data.remote.mapper.toSubscriptionService
import com.jefisu.trackizer.core.data.remote.model.SubscriptionRemote
import com.jefisu.trackizer.core.data.remote.model.SubscriptionServiceRemote
import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.domain.repository.SubscriptionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class SubscriptionRepositoryImpl(
    @Provided
    @Named(RemoteDataSourceQualifiers.SUBSCRIPTION)
    private val subsRemoteDataSource: RemoteDataSource<SubscriptionRemote>,
    @Provided
    @Named(RemoteDataSourceQualifiers.SUB_SERVICE)
    private val servRemoteDataSource: RemoteDataSource<SubscriptionServiceRemote>,
) : SubscriptionRepository {

    override val subscriptions: Flow<List<Subscription>> = servRemoteDataSource
        .data
        .onEmpty { emptyList<Subscription>() }
        .map { dataChanges -> dataChanges.map { it.document } }
        .combine(subsRemoteDataSource.data) { services, subDataChanges ->
            subDataChanges.mapNotNull { dataChange ->
                val subscriptionRemoteData = dataChange.document
                val serviceRemoteData = services
                    .firstOrNull { it.id == subscriptionRemoteData.serviceId }
                    ?: return@mapNotNull null
                subscriptionRemoteData.toSubscription(
                    service = serviceRemoteData.toSubscriptionService(),
                )
            }
        }

    override suspend fun addSubscription(subscription: Subscription): Result<Unit> {
        return runCatching {
            subsRemoteDataSource.add(subscription.toSubscriptionRemote())
        }
    }
}
