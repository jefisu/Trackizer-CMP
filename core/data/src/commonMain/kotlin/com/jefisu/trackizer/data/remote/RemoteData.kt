@file:OptIn(ExperimentalUuidApi::class)

package com.jefisu.trackizer.data.remote

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.serialization.Serializable

@Serializable
open class RemoteData(
    val id: String = Uuid.random().toHexString(),
)
