@file:OptIn(ExperimentalUuidApi::class)

package com.jefisu.trackizer.core.data.remote

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
open class RemoteData(
    @Transient open val id: String = generateId(),
) {
    companion object {
        fun generateId(): String = Uuid.random().toHexString()
    }
}
