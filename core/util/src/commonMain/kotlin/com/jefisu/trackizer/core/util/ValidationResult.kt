package com.jefisu.trackizer.core.util

data class ValidationResult<T>(val successfully: Boolean, val error: T? = null)
