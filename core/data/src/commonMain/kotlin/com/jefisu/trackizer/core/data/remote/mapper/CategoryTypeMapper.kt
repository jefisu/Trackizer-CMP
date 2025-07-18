package com.jefisu.trackizer.core.data.remote.mapper

import com.jefisu.trackizer.core.data.remote.model.CategoryTypeRemote
import com.jefisu.trackizer.core.domain.model.CategoryType
import com.jefisu.trackizer.core.util.ImageData

fun CategoryTypeRemote.toCategoryType(): CategoryType {
    return CategoryType(
        name = name,
        image = ImageData.Server(imageUrl),
        color = color,
    )
}
