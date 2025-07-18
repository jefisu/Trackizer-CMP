package com.jefisu.trackizer.data.repository

import com.jefisu.trackizer.data.di.RemoteDataSourceQualifiers
import com.jefisu.trackizer.data.remote.RemoteDataSource
import com.jefisu.trackizer.data.remote.mapper.toCategory
import com.jefisu.trackizer.data.remote.mapper.toCategoryType
import com.jefisu.trackizer.data.remote.model.CategoryRemote
import com.jefisu.trackizer.data.remote.model.CategoryTypeRemote
import com.jefisu.trackizer.domain.model.Category
import com.jefisu.trackizer.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import org.koin.core.annotation.Named
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single
class CategoryRepositoryImpl(
    @Provided
    @Named(RemoteDataSourceQualifiers.CATEGORY)
    private val categoryRemoteDataSource: RemoteDataSource<CategoryRemote>,
    @Provided
    @Named(RemoteDataSourceQualifiers.CATEGORY_TYPES)
    private val categoryTypeRemoteDataSource: RemoteDataSource<CategoryTypeRemote>,
) : CategoryRepository {

    override val categories: Flow<List<Category>> = categoryTypeRemoteDataSource
        .data
        .onEmpty { emptyList<Category>() }
        .map { dataChanges -> dataChanges.map { it.document } }
        .combine(categoryRemoteDataSource.data) { categoryTypes, categoryDataChanges ->
            categoryDataChanges.mapNotNull { dataChange ->
                val categoryRemoteData = dataChange.document
                val categoryType = categoryTypes
                    .firstOrNull { it.id == categoryRemoteData.typeId }
                    ?: return@mapNotNull null
                categoryRemoteData.toCategory(categoryType.toCategoryType())
            }
        }

    override suspend fun addCategory(category: Category) {
        TODO("Not yet implemented")
    }
}
