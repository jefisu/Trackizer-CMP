package com.jefisu.trackizer.core.util

data class ValidationRule<T, M : Message>(
    val validate: (T) -> Boolean,
    val error: M,
)
