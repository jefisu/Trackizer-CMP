package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.Gray100
import com.jefisu.trackizer.core.designsystem.Gray30
import com.jefisu.trackizer.core.designsystem.Gray60
import com.jefisu.trackizer.core.designsystem.Purple90
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.util.rippleClickable
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
fun AnimatedTabs(
    tabs: List<Tab>,
    modifier: Modifier = Modifier,
    shapeSize: Dp = 16.dp,
    initialSelectedIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    userScrollEnabled: Boolean = false,
    animationDurationMs: Int = 500,
) {
    require(tabs.isNotEmpty()) { "Tabs list cannot be empty" }
    require(
        initialSelectedIndex in tabs.indices,
    ) { "Initial selected index must be within tabs range" }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = initialSelectedIndex,
        pageCount = { tabs.size },
    )
    val selectedTabAnim = rememberSelectableTab(initialSelectedIndex.toFloat())

    val selectTabClick = fun(index: Int) {
        scope.launch {
            launch {
                selectedTabAnim.animateTo(
                    targetValue = index.toFloat(),
                    animationSpec = tween(animationDurationMs),
                )
            }
            launch {
                pagerState.animateScrollToPage(
                    page = index,
                    animationSpec = tween(animationDurationMs),
                )
            }
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTabAnim.animateTo(
            targetValue = pagerState.currentPage.toFloat(),
            animationSpec = tween(animationDurationMs),
        )
        onTabSelected(pagerState.currentPage)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(TrackizerTheme.spacing.medium),
    ) {
        Row(
            modifier = modifier
                .clip(RoundedCornerShape(shapeSize))
                .background(Gray100)
                .padding(TrackizerTheme.spacing.small)
                .selectedTab(
                    selectedIndex = { selectedTabAnim.value },
                    tabsCount = tabs.size,
                    cornerSize = shapeSize,
                ),
        ) {
            tabs.forEachIndexed { index, tab ->
                TabItem(
                    title = tab.title,
                    isSelected = index == selectedTabAnim.value.toInt(),
                    modifier = Modifier.weight(1f),
                    onClick = { selectTabClick(index) },
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = userScrollEnabled,
        ) { page ->
            tabs[page].content()
        }
    }
}

@Composable
private fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = Shapes().large,
) {
    val color by animateColorAsState(
        targetValue = if (isSelected) Color.White else Gray30,
        label = "title color",
    )

    Text(
        text = title,
        style = TrackizerTheme.typography.headline1,
        color = color,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clip(shape)
            .rippleClickable { onClick() }
            .padding(12.dp),
    )
}

private fun Modifier.selectedTab(selectedIndex: () -> Float, tabsCount: Int, cornerSize: Dp) =
    drawBehind {
        val cornerRadius = CornerRadius(cornerSize.toPx())
        val tabWidth = size.width / tabsCount
        val tabSize = size.copy(width = tabWidth)
        val offset = Offset(tabWidth * selectedIndex(), 0f)
        val tabCenterX = tabWidth / 2

        drawRoundRect(
            brush = Brush.linearGradient(
                colorStops = arrayOf(
                    0f to Purple90.copy(0.15f),
                    1f to Color.Transparent,
                ),
                start = offset,
                end = Offset(offset.x + tabCenterX, size.height),
            ),
            topLeft = offset,
            size = tabSize,
            cornerRadius = cornerRadius,
            style = Stroke(width = 1.dp.toPx()),
        )

        drawRoundRect(
            color = Gray60.copy(0.2f),
            topLeft = offset,
            size = tabSize,
            cornerRadius = cornerRadius,
        )
    }

@Composable
fun rememberSelectableTab(initialValue: Float = 0f) = rememberSaveable(
    saver = Saver(
        save = { it.value },
        restore = { Animatable(it) },
    ),
) {
    Animatable(initialValue)
}

data class Tab(
    val title: String,
    val content: @Composable () -> Unit,
)

@Preview
@Composable
private fun AnimatedTabsPreview(
    @PreviewParameter(AnimatedTabsPreviewParameter::class) tabs: List<Tab>,
) {
    TrackizerTheme {
        AnimatedTabs(
            tabs = tabs,
            modifier = Modifier.padding(TrackizerTheme.spacing.small),
        )
    }
}

private class AnimatedTabsPreviewParameter : PreviewParameterProvider<List<Tab>> {
    val tabs = listOf("Home", "Profile", "Settings").map {
        Tab(
            title = it,
            content = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                ) {
                    Text(
                        text = it,
                        style = TrackizerTheme.typography.headline5,
                        textAlign = TextAlign.Center,
                    )
                }
            },
        )
    }
    override val values = sequenceOf(
        tabs,
        tabs.dropLast(1).reversed(),
    )
}
