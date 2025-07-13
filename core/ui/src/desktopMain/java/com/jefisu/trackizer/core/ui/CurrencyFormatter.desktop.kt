package com.jefisu.trackizer.core.ui

actual class CurrencyFormatter {
    actual fun format(
        value: Double,
        isCompact: Boolean,
        currencyCode: String,
        country: String,
        languageTag: String,
    ): String {
        error("Not yet implemented on desktop")
    }
}
