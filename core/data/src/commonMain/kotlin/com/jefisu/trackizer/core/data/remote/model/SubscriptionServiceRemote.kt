@file:OptIn(ExperimentalUuidApi::class)

package com.jefisu.trackizer.core.data.remote.model

import com.jefisu.trackizer.core.data.remote.RemoteData
import kotlin.uuid.ExperimentalUuidApi
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionServiceRemote(
    val name: String = "",
    val imageUrl: String = "",
    val color: Long? = null,
    val userId: String? = null,
) : RemoteData()
