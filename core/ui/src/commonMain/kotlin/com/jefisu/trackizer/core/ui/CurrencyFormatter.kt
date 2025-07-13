package com.jefisu.trackizer.core.ui

expect class CurrencyFormatter() {
    fun format(
        value: Double,
        isCompact: Boolean,
        currencyCode: String,
        country: String,
        languageTag: String,
    ): String
}
