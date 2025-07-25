package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.adamglin.composeshadow.dropShadow
import com.jefisu.trackizer.core.designsystem.AccentPrimary100
import com.jefisu.trackizer.core.designsystem.FabIconColor
import com.jefisu.trackizer.core.designsystem.Gray30
import com.jefisu.trackizer.core.designsystem.Gray80
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.util.rippleClickable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import trackizer.core.designsystem.generated.resources.Res
import trackizer.core.designsystem.generated.resources.bg_bottom_navigation
import trackizer.core.designsystem.generated.resources.ic_error

@Composable
fun TrackizerBottomNavBar(
    navItems: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    initialSelectedIndex: Int = 0,
    centerFabIndex: Int = BottomNavConstants.DEFAULT_CENTER_FAB_INDEX,
) {
    require(navItems.size == BottomNavConstants.REQUIRED_ITEMS_COUNT) {
        "Bottom nav bar must have exactly ${BottomNavConstants.REQUIRED_ITEMS_COUNT} items"
    }
    require(centerFabIndex in navItems.indices) {
        "Center fab index ($centerFabIndex) out of range (0-${navItems.lastIndex})"
    }

    var selectedIndex by rememberSaveable { mutableIntStateOf(initialSelectedIndex) }

    BottomNavContainer(
        modifier = modifier,
    ) {
        BottomNavContent(
            navItems = navItems,
            selectedIndex = selectedIndex,
            centerFabIndex = centerFabIndex,
            onItemClick = { index, onClick ->
                selectedIndex = index
                onClick()
            },
        )
    }
}

@Preview
@Composable
private fun TrackizerBottomNavBarPreview() {
    TrackizerTheme {
        Box(
            modifier = Modifier
                .background(AccentPrimary100.copy(0.3f)),
        ) {
            TrackizerBottomNavBar(
                navItems = (1..5).map {
                    BottomNavItem(
                        icon = Res.drawable.ic_error,
                        onClick = {},
                    )
                },
            )
        }
    }
}

@Composable
private fun BottomNavContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(TrackizerTheme.size.bottomNavigationHeight)
            .background(
                Brush.verticalGradient(
                    BottomNavConstants.GRADIENT_TRANSPARENT_STOP to Color.Transparent,
                    BottomNavConstants.GRADIENT_VISIBLE_STOP to Gray80,
                ),
            ),
        content = content,
    )
}

@Composable
private fun BoxScope.BottomNavContent(
    navItems: List<BottomNavItem>,
    selectedIndex: Int,
    centerFabIndex: Int,
    onItemClick: (Int, () -> Unit) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomStart)
            .navigationBarsPadding()
            .padding(horizontal = TrackizerTheme.spacing.extraMedium)
            .paint(
                painter = painterResource(Res.drawable.bg_bottom_navigation),
                contentScale = ContentScale.FillWidth,
            ),
    ) {
        navItems.fastForEachIndexed { index, navItem ->
            when (index) {
                centerFabIndex -> {
                    CenterFabItem(
                        navItem = navItem,
                        modifier = Modifier.offset(y = BottomNavConstants.FAB_OFFSET_Y),
                    )
                }

                else -> {
                    RegularNavItem(
                        navItem = navItem,
                        isSelected = selectedIndex == index,
                        onClick = { onItemClick(index, navItem.onClick) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun CenterFabItem(navItem: BottomNavItem, modifier: Modifier = Modifier) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(BottomNavConstants.ANIMATION_DURATION),
        label = "fab_scale",
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(BottomNavConstants.FAB_CONTAINER_SIZE)
            .scale(scale),
    ) {
        FabBackground(
            onClick = navItem.onClick,
            icon = navItem.icon,
        )
    }
}

@Composable
private fun FabBackground(onClick: () -> Unit, icon: DrawableResource) {
    Box(
        modifier = Modifier
            .dropShadow(
                shape = CircleShape,
                color = AccentPrimary100.copy(BottomNavConstants.FAB_SHADOW_ALPHA),
                offsetY = BottomNavConstants.FAB_ELEVATION,
                blur = BottomNavConstants.FAB_BLUR,
            )
            .size(BottomNavConstants.FAB_CONTENT_SIZE)
            .drawWithContent {
                drawFabLayers()
                drawContent()
            }
            .clip(CircleShape)
            .rippleClickable(onClick = onClick)
            .paint(
                painter = painterResource(icon),
                colorFilter = ColorFilter.tint(Color.White),
            ),
    )
}

@Composable
private fun RegularNavItem(
    navItem: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val updateTransition = updateTransition(isSelected)
    val iconColor by updateTransition.animateColor(
        targetValueByState = { if (it) Color.White else Gray30 },
        transitionSpec = { tween(BottomNavConstants.ANIMATION_DURATION) },
        label = "icon_color",
    )

    val scale by updateTransition.animateFloat(
        targetValueByState = { if (it) 1.1f else 1f },
        transitionSpec = { tween(BottomNavConstants.ANIMATION_DURATION) },
        label = "icon_scale",
    )

    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .size(TrackizerTheme.size.iconDefault)
            .scale(scale)
            .paint(
                painter = painterResource(navItem.icon),
                colorFilter = ColorFilter.tint(iconColor),
            )
            .rippleClickable(
                indication = ripple(
                    bounded = false,
                    radius = BottomNavConstants.RIPPLE_RADIUS,
                ),
                onClick = onClick,
            ),
    )
}

private fun DrawScope.drawFabLayers() {
    drawFabBackground()
    drawFabInnerGradient()
    drawFabStroke()
}

private fun DrawScope.drawFabBackground() {
    drawCircle(color = AccentPrimary100)
}

private fun DrawScope.drawFabInnerGradient() {
    drawCircle(
        brush = Brush.radialGradient(
            colorStops = arrayOf(
                BottomNavConstants.FAB_INNER_GRADIENT_START to Color.Transparent,
                1f to FabIconColor.copy(BottomNavConstants.FAB_SHADOW_ALPHA),
            ),
        ),
    )
}

private fun DrawScope.drawFabStroke() {
    drawCircle(
        brush = Brush.linearGradient(
            colorStops = arrayOf(
                0f to Color.White.copy(BottomNavConstants.FAB_STROKE_ALPHA),
                1f to Color.Transparent,
            ),
            end = Offset(x = center.x, y = size.height),
        ),
        style = Stroke(width = BottomNavConstants.FAB_STROKE_WIDTH.toPx()),
    )
}

data class BottomNavItem(
    val icon: DrawableResource,
    val onClick: () -> Unit,
)

private object BottomNavConstants {
    const val REQUIRED_ITEMS_COUNT = 5
    const val DEFAULT_CENTER_FAB_INDEX = 2

    val FAB_CONTAINER_SIZE = 66.dp
    val FAB_CONTENT_SIZE = 56.dp
    val FAB_ELEVATION = 8.dp
    val FAB_BLUR = 25.dp
    val FAB_STROKE_WIDTH = 2.dp
    val FAB_OFFSET_Y = (-16).dp

    val RIPPLE_RADIUS = 24.dp

    const val GRADIENT_TRANSPARENT_STOP = 0f
    const val GRADIENT_VISIBLE_STOP = 0.65f
    const val FAB_INNER_GRADIENT_START = 0.43f
    const val FAB_SHADOW_ALPHA = 0.5f
    const val FAB_STROKE_ALPHA = 0.5f

    const val ANIMATION_DURATION = 300
}
