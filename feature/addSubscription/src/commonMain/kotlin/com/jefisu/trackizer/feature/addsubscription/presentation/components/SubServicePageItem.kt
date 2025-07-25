package com.jefisu.trackizer.feature.addsubscription.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.TrackizerSubscriptionIcon
import com.jefisu.trackizer.core.designsystem.util.previewSubscriptions
import com.jefisu.trackizer.core.domain.model.SubscriptionService
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SubServicePageItem(service: SubscriptionService, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        TrackizerSubscriptionIcon(
            imageData = service.imageData,
            containerSize = 161.dp,
            cornerSize = 50.dp,
            iconSize = TrackizerTheme.size.iconExtraLarge,
        )
        Spacer(Modifier.height(TrackizerTheme.spacing.extraMedium))
        Text(
            text = service.name,
            style = TrackizerTheme.typography.headline3,
        )
    }
}

@Preview
@Composable
private fun SubscriptionServicePageItemPreview() {
    val service = previewSubscriptions.first().service
    TrackizerTheme {
        SubServicePageItem(
            service = service,
            modifier = Modifier
                .padding(TrackizerTheme.spacing.large),
        )
    }
}
