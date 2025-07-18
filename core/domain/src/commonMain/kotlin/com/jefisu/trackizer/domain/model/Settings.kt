package com.jefisu.trackizer.domain.model

data class Settings(
    val isCloudSyncEnabled: Boolean,
    val languageTag: String,
    val currency: Currency,
)
