package com.jefisu.trackizer.core.data.repository

import com.jefisu.trackizer.core.data.di.RemoteDataSourceQualifiers
import com.jefisu.trackizer.core.data.remote.RemoteDataSource
import com.jefisu.trackizer.core.data.remote.mapper.toSubscriptionService
import com.jefisu.trackizer.core.data.remote.model.SubscriptionServiceRemote
import com.jefisu.trackizer.core.domain.repository.SubServicesRepository
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class SubServicesRepositoryImpl(
    @Provided
    @Named(RemoteDataSourceQualifiers.SUB_SERVICE)
    private val remoteDataSource: RemoteDataSource<SubscriptionServiceRemote>,
) : SubServicesRepository {
    override val services = remoteDataSource.data.map { dataChanges ->
        dataChanges.map { it.document.toSubscriptionService() }
    }
}
