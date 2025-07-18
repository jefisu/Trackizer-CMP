@file:OptIn(ExperimentalTime::class)

package com.jefisu.trackizer.core.util

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun nowUtc(): LocalDateTime {
    return Clock
        .System
        .now()
        .toLocalDateTime(TimeZone.UTC)
}
