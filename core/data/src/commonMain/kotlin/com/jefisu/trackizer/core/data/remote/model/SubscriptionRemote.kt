@file:OptIn(ExperimentalTime::class)

package com.jefisu.trackizer.core.data.remote.model

import com.jefisu.trackizer.core.data.remote.RemoteData
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionRemote(
    val price: Float = 0f,
    val description: String = "",
    val firstPaymentSeconds: Long = Clock.System.now().epochSeconds,
    val reminder: Boolean = false,
    val serviceId: String = "",
    val userId: String? = Firebase.auth.currentUser?.uid,
    override val id: String = RemoteData.generateId(),
) : RemoteData()
