package com.jefisu.trackizer.core.domain.model

data class Currency(
    val code: String,
    val symbol: String,
    val country: String,
    val name: String,
    val languageTag: String,
    val thousandsSeparator: Char = ',',
    val decimalSeparator: Char = '.',
    val zeroDigit: Char = '0',
)
