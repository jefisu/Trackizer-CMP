package com.jefisu.trackizer.core.domain.model

data class Settings(
    val isCloudSyncEnabled: Boolean,
    val languageTag: String,
    val currency: Currency,
)
