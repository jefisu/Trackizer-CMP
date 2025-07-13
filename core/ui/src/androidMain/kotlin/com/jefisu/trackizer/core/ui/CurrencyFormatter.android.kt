package com.jefisu.trackizer.core.ui

import android.icu.text.CompactDecimalFormat
import java.text.NumberFormat
import java.util.Locale

actual class CurrencyFormatter {
    actual fun format(
        value: Double,
        isCompact: Boolean,
        currencyCode: String,
        country: String,
        languageTag: String,
    ): String {
        val locale = Locale(languageTag, country)
        val numberFormat = NumberFormat.getCurrencyInstance(locale)

        val floors = listOf(1e9, 1e6, 1e3)
        return if (isCompact && floors.any { value >= it }) {
            val compactDecimalFormat = CompactDecimalFormat.getInstance(
                locale,
                CompactDecimalFormat.CompactStyle.SHORT,
            )
            val formattedValue = compactDecimalFormat.format(value)
            val symbol = numberFormat.currency?.symbol?.removePrefix(locale.country)
            "$symbol $formattedValue"
        } else {
            numberFormat.format(value).removePrefix(locale.country)
        }
    }
}
