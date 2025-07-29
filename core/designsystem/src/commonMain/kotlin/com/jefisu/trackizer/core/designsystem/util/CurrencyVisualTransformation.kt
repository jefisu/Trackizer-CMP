package com.jefisu.trackizer.core.designsystem.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import com.jefisu.trackizer.core.domain.model.Currency

internal class CurrencyVisualTransformation(
    val currency: Currency,
) : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val numberOfDecimals = 2
        val decimalSeparator = currency.decimalSeparator
        val originalText = text.text
        val zero = currency.zeroDigit

        val intPart = originalText
            .dropLast(numberOfDecimals)
            .reversed()
            .chunked(3)
            .joinToString(currency.thousandsSeparator.toString())
            .reversed()
            .ifEmpty(zero::toString)

        val fractionPart = originalText.takeLast(numberOfDecimals).let {
            val length = it.length
            if (length == numberOfDecimals) return@let it
            List(
                size = numberOfDecimals - length,
                init = { zero },
            ).joinToString("") + it
        }

        val formattedNumber = intPart + decimalSeparator + fractionPart

        val newText = AnnotatedString(
            text = "${currency.symbol} $formattedNumber",
            spanStyles = text.spanStyles,
            paragraphStyles = text.paragraphStyles,
        )

        return TransformedText(
            newText,
            FixedCursorOffsetMapping(
                contentLength = originalText.length,
                formattedContentLength = newText.length,
            ),
        )
    }
}

private class FixedCursorOffsetMapping(
    private val contentLength: Int,
    private val formattedContentLength: Int,
) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int = formattedContentLength
    override fun transformedToOriginal(offset: Int): Int = contentLength
}
