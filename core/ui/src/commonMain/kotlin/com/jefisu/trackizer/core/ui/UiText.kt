package com.jefisu.trackizer.core.ui

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    data class StringRes(val res: StringResource, val args: Array<Argument> = emptyArray()) : UiText

    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is StringRes -> {
            val args = args.map { it.formatToDisplay() }

            @Suppress("SpreadOperator")
            stringResource(res, *args.toTypedArray())
        }
    }
}

data class Argument(val value: Any, val format: Format = Format.NORMAL) {

    @Composable
    fun formatToDisplay(): Any {
        val valueRes = getStringFromArg(value)
        if (valueRes !is String) return value
        return when (format) {
            Format.UPPERCASE -> valueRes.uppercase()
            Format.LOWERCASE -> valueRes.lowercase()
            Format.CAPITALIZE -> valueRes.replaceFirstChar { it.titlecase() }
            Format.NORMAL -> valueRes
        }
    }

    @Composable
    private fun getStringFromArg(arg: Any): Any {
        if (arg !is StringResource) return arg
        val result = runCatching { stringResource(arg) }
        return result.getOrNull() ?: "(Invalid arg resource: $arg)"
    }

    enum class Format {
        NORMAL,
        UPPERCASE,
        LOWERCASE,
        CAPITALIZE,
    }
}
