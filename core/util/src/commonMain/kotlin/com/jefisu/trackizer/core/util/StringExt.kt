package com.jefisu.trackizer.core.util

fun String.toMoneyValue(): Float {
    if (all { it.isDigit() } && isNotBlank()) return toFloat() / 100f
    return 0f
}
