package com.jefisu.trackizer.core.ui

import androidx.compose.runtime.compositionLocalOf
import com.jefisu.trackizer.core.domain.model.Currency
import com.jefisu.trackizer.core.domain.model.Settings

val LocalSettings = compositionLocalOf { previewSettings }

private val previewSettings = Settings(
    isCloudSyncEnabled = false,
    languageTag = "en",
    currency = Currency(
        code = "USD",
        symbol = "$",
        country = "US",
        name = "US Dollar",
        languageTag = "en",
    ),
)
