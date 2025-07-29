package com.jefisu.trackizer.core.ui

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@Composable
fun rememberEndlessPagerState(startPage: Int = 0) = rememberPagerState(
    pageCount = { Int.MAX_VALUE },
    initialPage = startPage,
)

fun <T> List<T>.getEndlessItem(index: Int) = this[index % size]

fun PagerState.currentOffsetForPage(page: Int): Float =
    (currentPage - page) + currentPageOffsetFraction

/**
 * Applies 3D spherical animation to HorizontalPager items
 */
fun Modifier.sphericalPagerAnimation(
    pagerState: PagerState,
    page: Int,
    minScale: Float = 0.8f,
    maxScale: Float = 1f,
    rotationZ: Float = 10f,
    rotationX: Float = 5f,
    cameraDistance: Float = 12f,
    enableAlpha: Boolean = true,
    minAlpha: Float = 0.7f,
): Modifier = this.graphicsLayer {
    val pageOffset = pagerState.currentOffsetForPage(page)
    val absOffset = pageOffset.absoluteValue

    // Scale - increases when approaching center (selected)
    val scale = lerp(
        start = minScale,
        stop = maxScale,
        fraction = 1f - absOffset.coerceIn(0f, 1f),
    )
    scaleY = scale
    scaleX = scale

    // Spherical rotation - combining Y and Z rotation
    this@graphicsLayer.rotationY = lerp(
        start = 0f,
        stop = rotationY,
        fraction = absOffset.coerceIn(0f, 1f),
    ) * if (pageOffset > 0) 1f else -1f

    // Z-axis rotation to simulate spherical curvature
    this@graphicsLayer.rotationZ = lerp(
        start = 0f,
        stop = rotationZ,
        fraction = absOffset.coerceIn(0f, 1f),
    ) * if (pageOffset > 0) -1f else 1f

    // X-axis rotation to complete the spherical effect
    this@graphicsLayer.rotationX = lerp(
        start = 0f,
        stop = rotationX,
        fraction = absOffset.coerceIn(0f, 1f),
    )

    // Adjust depth with cameraDistance for perspective
    this@graphicsLayer.cameraDistance = cameraDistance * density

    // Alpha transparency to reinforce depth
    if (enableAlpha) {
        alpha = lerp(
            start = minAlpha,
            stop = 1f,
            fraction = 1f - absOffset.coerceIn(0f, 1f),
        )
    }
}
