package com.jefisu.trackizer.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.Gray70
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.TrackizerSubscriptionIcon
import com.jefisu.trackizer.core.designsystem.components.currencyText
import com.jefisu.trackizer.core.designsystem.util.previewSubscriptions
import com.jefisu.trackizer.core.util.rippleClickable
import com.jefisu.trackizer.domain.model.Subscription
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import trackizer.feature.home.generated.resources.Res
import trackizer.feature.home.generated.resources.you_don_t_have_any

@Composable
internal fun SubscriptionList(
    subscriptions: List<Subscription>,
    messageEmptyList: String,
    modifier: Modifier = Modifier,
    upcomingBill: Boolean = false,
    onSubscriptionClick: (Subscription) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(),
) {
    AnimatedContent(
        targetState = subscriptions,
        contentAlignment = Alignment.Center,
        transitionSpec = {
            val animationSpec = tween<IntOffset>(durationMillis = 700)
            fadeIn() + slideInVertically(
                animationSpec = animationSpec,
            ) togetherWith fadeOut() + slideOutVertically(
                animationSpec = animationSpec,
            )
        },
        modifier = modifier.windowInsetsPadding(
            WindowInsets.safeContent.only(WindowInsetsSides.Bottom),
        ),
    ) { subs ->
        if (subs.isEmpty()) {
            EmptySubscriptionList(
                message = messageEmptyList,
                contentPadding = contentPadding,
            )
        } else {
            SubscriptionListContent(
                subscriptions = subs,
                upcomingBill = upcomingBill,
                onSubscriptionClick = onSubscriptionClick,
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
private fun rememberShowDivider(lazyListState: LazyListState): State<Boolean> {
    return remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0 ||
                lazyListState.firstVisibleItemScrollOffset > 0
        }
    }
}

@Composable
private fun EmptySubscriptionList(message: String, contentPadding: PaddingValues) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            color = Gray50,
            style = TrackizerTheme.typography.bodyLarge,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = contentPadding.calculateBottomPadding()),
        )
    }
}

@Composable
private fun SubscriptionListContent(
    subscriptions: List<Subscription>,
    upcomingBill: Boolean,
    onSubscriptionClick: (Subscription) -> Unit,
    contentPadding: PaddingValues,
) {
    val lazyListState = rememberLazyListState()
    val showDivider by rememberShowDivider(lazyListState)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(TrackizerTheme.spacing.small),
        contentPadding = contentPadding,
        state = lazyListState,
        modifier = Modifier.drawWithContent {
            drawContent()
            if (showDivider) {
                drawTopDivider()
            }
        },
    ) {
        items(
            items = subscriptions,
            key = { it.id },
        ) { subscription ->
            SubscriptionItem(
                subscription = subscription,
                upcomingBill = upcomingBill,
                onClick = { onSubscriptionClick(subscription) },
            )
        }
    }
}

private fun DrawScope.drawTopDivider() {
    val dividerThickness = DividerDefaults.Thickness.toPx()
    drawLine(
        color = Gray50,
        start = Offset(0f, dividerThickness),
        end = Offset(size.width, dividerThickness),
        strokeWidth = dividerThickness,
    )
}

@Composable
private fun SubscriptionItem(
    subscription: Subscription,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    upcomingBill: Boolean = false,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(TrackizerTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shapes().large)
            .border(
                width = 1.dp,
                color = Gray70,
                shape = Shapes().large,
            )
            .rippleClickable { onClick() }
            .padding(
                top = 12.dp,
                bottom = 12.dp,
                start = 12.dp,
                end = 17.dp,
            )
            .then(modifier),
    ) {
        if (upcomingBill) {
            DateIcon(subscription.firstPayment.date)
        } else {
            TrackizerSubscriptionIcon(subscription.serviceType.imageData)
        }
        Text(
            text = subscription.serviceType.name,
            style = TrackizerTheme.typography.headline2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier.weight(1f),
        )
        Text(
            text = currencyText(
                value = subscription.price.toDouble(),
                isCompactFormat = false,
            ),
            style = TrackizerTheme.typography.headline2,
        )
    }
}

@Preview
@Composable
private fun SubscriptionListPreview(
    @PreviewParameter(SubscriptionListPreviewParameter::class) subscriptions: List<Subscription>,
) {
    TrackizerTheme {
        SubscriptionList(
            subscriptions = subscriptions,
            messageEmptyList = stringResource(Res.string.you_don_t_have_any, ""),
            modifier = Modifier
                .aspectRatio(1f)
                .padding(TrackizerTheme.spacing.medium),
        )
    }
}

private class SubscriptionListPreviewParameter : PreviewParameterProvider<List<Subscription>> {
    override val values = sequenceOf(
        previewSubscriptions,
        emptyList(),
    )
}
