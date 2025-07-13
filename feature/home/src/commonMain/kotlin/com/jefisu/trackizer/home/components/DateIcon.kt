package com.jefisu.trackizer.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.TrackizerIconContainer
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun DateIcon(date: LocalDate) {
    val month = date.month.name.take(3)
        .lowercase()
        .replaceFirstChar { it.uppercase() }

    TrackizerIconContainer {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = month,
                style = TrackizerTheme.typography.bodyMedium,
                fontSize = 10.sp,
            )
            Text(
                text = date.day.toString(),
                style = TrackizerTheme.typography.headline2,
            )
        }
    }
}

@Preview
@Composable
private fun DateIconPreview() {
    TrackizerTheme {
        DateIcon(LocalDate(2024, 7, 10))
    }
}
