package com.jefisu.trackizer.data.remote

import kotlinx.coroutines.flow.Flow

interface RemoteDataSource<T : RemoteData> {
    val data: Flow<List<RemoteDataChange<T>>>
}
