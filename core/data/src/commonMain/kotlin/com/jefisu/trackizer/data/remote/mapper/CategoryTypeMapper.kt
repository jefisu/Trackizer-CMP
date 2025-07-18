package com.jefisu.trackizer.data.remote.mapper

import com.jefisu.trackizer.core.util.ImageData
import com.jefisu.trackizer.data.remote.model.CategoryTypeRemote
import com.jefisu.trackizer.domain.model.CategoryType

fun CategoryTypeRemote.toCategoryType(): CategoryType {
    return CategoryType(
        name = name,
        image = ImageData.Server(imageUrl),
        color = color,
    )
}
