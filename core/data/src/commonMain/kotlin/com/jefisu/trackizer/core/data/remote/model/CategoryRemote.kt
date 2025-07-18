@file:OptIn(ExperimentalTime::class)

package com.jefisu.trackizer.core.data.remote.model

import com.jefisu.trackizer.core.data.remote.RemoteData
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlin.time.ExperimentalTime
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRemote(
    val name: String = "",
    val budget: Float = 0f,
    val typeId: String = "",
    val userId: String? = Firebase.auth.currentUser?.uid,
) : RemoteData()
