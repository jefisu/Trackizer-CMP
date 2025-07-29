@file:OptIn(ExperimentalTime::class)

package com.jefisu.trackizer.core.data.remote.mapper

import com.jefisu.trackizer.core.data.remote.RemoteData
import com.jefisu.trackizer.core.data.remote.model.SubscriptionRemote
import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.domain.model.SubscriptionService
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun SubscriptionRemote.toSubscription(service: SubscriptionService): Subscription {
    return Subscription(
        service = service,
        description = description,
        price = price,
        firstPayment = Instant
            .fromEpochSeconds(firstPaymentSeconds)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        reminder = reminder,
        id = id,
    )
}

fun Subscription.toSubscriptionRemote(): SubscriptionRemote {
    return SubscriptionRemote(
        serviceId = service.id,
        description = description,
        price = price,
        firstPaymentSeconds = firstPayment.toInstant(TimeZone.UTC).epochSeconds,
        reminder = reminder,
        id = id.ifEmpty(RemoteData::generateId),
    )
}
