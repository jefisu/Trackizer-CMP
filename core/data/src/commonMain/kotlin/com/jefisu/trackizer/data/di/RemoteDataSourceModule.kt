package com.jefisu.trackizer.data.di

import com.jefisu.trackizer.data.remote.FirestoreRemoteDataSource
import com.jefisu.trackizer.data.remote.RemoteDataSource
import com.jefisu.trackizer.data.remote.model.CategoryRemote
import com.jefisu.trackizer.data.remote.model.CategoryTypeRemote
import com.jefisu.trackizer.data.remote.model.SubscriptionRemote
import com.jefisu.trackizer.data.remote.model.SubscriptionServiceRemote
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
class RemoteDataSourceModule {

    @Named(RemoteDataSourceQualifiers.SUBSCRIPTION)
    @Single
    fun provideSubscriptionRemoteDataSource(): RemoteDataSource<SubscriptionRemote> {
        return FirestoreRemoteDataSource(
            collectionPath = "subscriptions",
            clazz = SubscriptionRemote::class,
        )
    }

    @Named(RemoteDataSourceQualifiers.SUB_SERVICE)
    @Single
    fun provideSubscriptionServiceRemoteDataSource(): RemoteDataSource<SubscriptionServiceRemote> {
        return FirestoreRemoteDataSource(
            collectionPath = "subscriptions_services",
            clazz = SubscriptionServiceRemote::class,
        )
    }

    @Named(RemoteDataSourceQualifiers.CATEGORY)
    @Single
    fun provideCategoryRemoteDataSource(): RemoteDataSource<CategoryRemote> {
        return FirestoreRemoteDataSource(
            collectionPath = "categories",
            clazz = CategoryRemote::class,
        )
    }

    @Named(RemoteDataSourceQualifiers.CATEGORY_TYPES)
    @Single
    fun provideCategoryTypeRemoteDataSource(): RemoteDataSource<CategoryTypeRemote> {
        return FirestoreRemoteDataSource(
            collectionPath = "categories_types",
            clazz = CategoryTypeRemote::class,
        )
    }
}
