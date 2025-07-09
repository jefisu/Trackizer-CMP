package com.jefisu.trackizer.core.util

fun interface Validator<V, M> {
    fun validate(value: V): ValidationResult<M>
}
