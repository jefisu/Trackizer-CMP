package com.jefisu.trackizer.core.data.remote

import kotlinx.coroutines.flow.Flow

interface RemoteDataSource<T : RemoteData> {
    val data: Flow<List<RemoteDataChange<T>>>
    suspend fun add(obj: T): Result<Unit>
}
