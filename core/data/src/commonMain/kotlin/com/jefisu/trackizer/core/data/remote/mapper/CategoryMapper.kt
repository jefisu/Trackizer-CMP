@file:OptIn(ExperimentalTime::class)

package com.jefisu.trackizer.core.data.remote.mapper

import com.jefisu.trackizer.core.data.remote.model.CategoryRemote
import com.jefisu.trackizer.core.domain.model.Category
import com.jefisu.trackizer.core.domain.model.CategoryType
import kotlin.time.ExperimentalTime

fun CategoryRemote.toCategory(type: CategoryType): Category {
    return Category(
        name = name,
        type = type,
        budget = budget,
        id = id,
    )
}
