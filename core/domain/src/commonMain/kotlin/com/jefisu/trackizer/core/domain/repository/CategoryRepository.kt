package com.jefisu.trackizer.core.domain.repository

import com.jefisu.trackizer.core.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    val categories: Flow<List<Category>>
    suspend fun addCategory(category: Category)
}
