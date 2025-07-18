package com.jefisu.trackizer.core.domain.model

import com.jefisu.trackizer.core.util.ImageData

data class CategoryType(
    val name: String,
    val image: ImageData,
    val color: Long?,
)
