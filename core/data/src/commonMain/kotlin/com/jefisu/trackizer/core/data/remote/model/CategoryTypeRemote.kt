package com.jefisu.trackizer.core.data.remote.model

import com.jefisu.trackizer.core.data.remote.RemoteData
import kotlinx.serialization.Serializable

@Serializable
data class CategoryTypeRemote(
    val name: String = "",
    val imageUrl: String = "",
    val color: Long? = null,
    val userId: String? = null,
    override val id: String = RemoteData.generateId(),
) : RemoteData()
