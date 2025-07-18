package com.jefisu.trackizer.domain.repository

import com.jefisu.trackizer.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    val categories: Flow<List<Category>>
    suspend fun addCategory(category: Category)
}
