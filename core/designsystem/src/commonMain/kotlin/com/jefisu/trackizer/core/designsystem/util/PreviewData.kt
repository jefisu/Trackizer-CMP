@file:OptIn(ExperimentalTime::class)

package com.jefisu.trackizer.core.designsystem.util

import com.jefisu.trackizer.core.domain.model.Category
import com.jefisu.trackizer.core.domain.model.CategoryType
import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.domain.model.SubscriptionService
import com.jefisu.trackizer.core.util.ImageData
import kotlin.time.ExperimentalTime
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

val previewSubscriptions = PredefinedSubscriptionServices.entries.mapIndexed { index, service ->
    val timeZone = TimeZone.currentSystemDefault()
    val dateTime = LocalDateTime(2024, 7, 10, 22, 15)
        .toInstant(timeZone)
        .plus(DatePeriod(months = index), timeZone)
        .toLocalDateTime(timeZone)

    Subscription(
        id = index.toString(),
        description = "Subscription $index",
        service = SubscriptionService(
            name = service.displayName,
            imageData = ImageData.Device(service.imageRes),
            id = "${service.displayName}_$index",
            color = service.color.value.toLong(),
        ),
        price = index.toFloat(),
        firstPayment = dateTime,
        reminder = index % 2 == 0,
    )
}

val previewCategories = listOf(
    Category(
        name = "Concert",
        budget = 50f,
        type = CategoryType(
            name = "Entertainment",
            image = ImageData.Server("example.com"),
            color = 0xFF000000,
        ),
        id = "",
        usedBudget = 10f,
    ),
    Category(
        name = "Japanese food",
        budget = 100f,
        type = CategoryType(
            name = "Food",
            image = ImageData.Server("example.com"),
            color = 0xFFFF0000,
        ),
        id = "",
        usedBudget = 30f,
    ),
)
