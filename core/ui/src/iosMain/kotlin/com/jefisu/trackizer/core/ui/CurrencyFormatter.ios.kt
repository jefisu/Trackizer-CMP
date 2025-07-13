package com.jefisu.trackizer.core.ui

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSNumberFormatterDecimalStyle

actual class CurrencyFormatter {
    actual fun format(
        value: Double,
        isCompact: Boolean,
        currencyCode: String,
        country: String,
        languageTag: String,
    ): String {
        val locale = NSLocale(localeIdentifier = languageTag)
        val numberFormatter = NSNumberFormatter().also {
            it.numberStyle = NSNumberFormatterCurrencyStyle
            it.locale = locale
            it.currencyCode = currencyCode
        }

        val floors = listOf(1e9, 1e6, 1e3)
        return if (isCompact && floors.any { value >= it }) {
            val compactFormatter = NSNumberFormatter().also {
                it.numberStyle = NSNumberFormatterDecimalStyle
                it.locale = locale
            }
            val (scaledValue, suffix) = when {
                value >= 1e9 -> value / 1e9 to "B"
                value >= 1e6 -> value / 1e6 to "M"
                value >= 1e3 -> value / 1e3 to "K"
                else -> value to ""
            }
            compactFormatter.maximumFractionDigits = if (scaledValue >= 10) 0u else 1u
            val formattedValue = compactFormatter.stringFromNumber(NSNumber(scaledValue))
            val symbol = numberFormatter.currencySymbol
            "$symbol $formattedValue$suffix"
        } else {
            val formattedCurrency = numberFormatter
                .stringFromNumber(NSNumber(value))
                .orEmpty()
            formattedCurrency.removePrefix(country)
        }
    }
}
