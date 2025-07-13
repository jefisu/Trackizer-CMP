package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.composeunstyled.Text
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.ui.CurrencyFormatter
import com.jefisu.trackizer.core.ui.LocalSettings
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun currencyText(value: Double, isCompactFormat: Boolean = true): String {
    val settings = LocalSettings.current
    val currencyFormatter = remember { CurrencyFormatter() }
    return remember(value, settings.currency) {
        currencyFormatter.format(
            value = value,
            isCompact = isCompactFormat,
            currencyCode = settings.currency.code,
            country = settings.currency.country,
            languageTag = settings.languageTag,
        )
    }
}

@Preview
@Composable
fun CurrencyTextPreview() {
    TrackizerTheme {
        Text(
            text = currencyText(100.0),
            color = Color.White,
        )
    }
}
