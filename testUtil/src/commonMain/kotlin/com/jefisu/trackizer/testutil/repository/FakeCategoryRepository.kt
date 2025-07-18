package com.jefisu.trackizer.testutil.repository

import com.jefisu.trackizer.core.domain.model.Category
import com.jefisu.trackizer.core.domain.repository.CategoryRepository
import kotlin.collections.plus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FakeCategoryRepository : CategoryRepository {

    private val _categories = MutableStateFlow(emptyList<Category>())
    override val categories = _categories.asStateFlow()

    override suspend fun addCategory(category: Category) {
        _categories.update { it + category }
    }
}
