package com.jefisu.trackizer.feature.home.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.AccentPrimary100
import com.jefisu.trackizer.core.designsystem.AccentPrimary50
import com.jefisu.trackizer.core.designsystem.AccentSecondary50
import com.jefisu.trackizer.core.designsystem.Black18
import com.jefisu.trackizer.core.designsystem.Gray40
import com.jefisu.trackizer.core.designsystem.Gray60
import com.jefisu.trackizer.core.designsystem.Gray70
import com.jefisu.trackizer.core.designsystem.Primary10
import com.jefisu.trackizer.core.designsystem.Purple90
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.AnimatedText
import com.jefisu.trackizer.core.designsystem.components.AutoResizedText
import com.jefisu.trackizer.core.designsystem.components.ButtonType
import com.jefisu.trackizer.core.designsystem.components.TrackizerButton
import com.jefisu.trackizer.core.designsystem.components.TrackizerLogoDefaults
import com.jefisu.trackizer.core.designsystem.components.currencyText
import com.jefisu.trackizer.core.designsystem.util.previewSubscriptions
import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.util.drawBlur
import com.jefisu.trackizer.core.util.nowUtc
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import trackizer.feature.home.generated.resources.Res
import trackizer.feature.home.generated.resources.active_subs
import trackizer.feature.home.generated.resources.highest_subs
import trackizer.feature.home.generated.resources.lowest_subs
import trackizer.feature.home.generated.resources.see_your_budget
import trackizer.feature.home.generated.resources.this_month_bills

@Composable
internal fun SubscriptionDashboard(
    subscriptions: List<Subscription>,
    monthlyBudget: Double,
    onSeeBudgetClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dashboardData = remember(subscriptions, monthlyBudget) {
        SubscriptionDashboardData.from(subscriptions, monthlyBudget)
    }

    val budgetCommittedPercentage by animateFloatAsState(
        targetValue = dashboardData.budgetCommittedPercentage,
        label = "budget_percentage_animation",
        animationSpec = tween(
            durationMillis = ArcConfig.ANIMATION_DURATION,
            easing = LinearEasing,
        ),
    )

    val density = LocalDensity.current
    var arcHeight by remember { mutableStateOf(0.dp) }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(0.dp, 0.dp, 24.dp, 24.dp))
            .background(Gray70)
            .statusBarsPadding(),
    ) {
        ArcProgressIndicator(
            progress = { budgetCommittedPercentage },
            modifier = Modifier
                .onSizeChanged {
                    arcHeight = with(density) { it.height.toDp() }
                },
            centerContent = {
                DashboardCenterContent(
                    monthlyBillsValue = dashboardData.monthlyBillsValue,
                    onSeeBudgetClick = onSeeBudgetClick,
                )
            },
        )
        SubscriptionInfoRow(
            activeSubs = dashboardData.activeSubs,
            highestSubsValue = dashboardData.highestSubsValue,
            lowestSubsValue = dashboardData.lowestSubsValue,
            modifier = Modifier
                .padding(TrackizerTheme.spacing.extraMedium)
                .padding(top = arcHeight * 0.8f),
        )
    }
}

@Composable
private fun DashboardCenterContent(monthlyBillsValue: Double, onSeeBudgetClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TrackizerTheme.spacing.large + TrackizerTheme.spacing.small),
    ) {
        TrackizerLogoDefaults.Logo(
            size = 20.dp,
            textStyle = TrackizerTheme.typography.headline3,
        )
        Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
        BillsAmount(monthlyBillsValue)
        Spacer(modifier = Modifier.height(TrackizerTheme.spacing.medium))
        BillsDescription()
        Spacer(modifier = Modifier.weight(1f))
        BudgetButton(onSeeBudgetClick)
    }
}

@Composable
private fun BillsAmount(monthlyBillsValue: Double) {
    AnimatedText(
        text = currencyText(monthlyBillsValue),
        style = TrackizerTheme.typography.headline7,
    )
}

@Composable
private fun BillsDescription() {
    Text(
        text = stringResource(Res.string.this_month_bills),
        style = TrackizerTheme.typography.headline2,
        color = Gray40,
    )
}

@Composable
private fun BudgetButton(onSeeBudgetClick: () -> Unit) {
    TrackizerButton(
        text = stringResource(Res.string.see_your_budget),
        type = ButtonType.Secondary,
        onClick = onSeeBudgetClick,
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .height(TrackizerTheme.size.buttonHeightSmall)
            .width(130.dp),
    )
}

@Composable
private fun SubscriptionInfoRow(
    activeSubs: Int,
    highestSubsValue: Double,
    lowestSubsValue: Double,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        SubscriptionInfoItem(
            title = stringResource(Res.string.active_subs),
            value = activeSubs.toString(),
            highlightedColor = AccentPrimary50,
            modifier = Modifier.weight(1f),
        )
        SubscriptionInfoItem(
            title = stringResource(Res.string.highest_subs),
            value = currencyText(highestSubsValue),
            highlightedColor = Primary10,
            modifier = Modifier.weight(1f),
        )
        SubscriptionInfoItem(
            title = stringResource(Res.string.lowest_subs),
            value = currencyText(lowestSubsValue),
            highlightedColor = AccentSecondary50,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ArcProgressIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    centerContent: (@Composable BoxScope.() -> Unit)? = null,
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .drawWithContent {
                drawArcLayers(
                    progress = progress(),
                    drawContent = ::drawContent,
                )
            },
    ) {
        DropShadowBackground()
        centerContent?.let {
            Box(
                modifier = Modifier
                    .fillMaxWidth(ArcScales.CENTER_WIDTH)
                    .fillMaxHeight(ArcScales.CENTER_HEIGHT),
                content = it,
            )
        }
    }
}

@Composable
private fun BoxWithConstraintsScope.DropShadowBackground() {
    Box(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .width(maxWidth)
            .height(maxHeight / 2)
            .background(
                Brush.verticalGradient(
                    0f to Black18.copy(0f),
                    0.61f to Black18,
                ),
            ),
    )
}

private fun DrawScope.drawArcLayers(progress: Float, drawContent: () -> Unit) {
    drawRect(color = Gray70)

    drawDashedArc(
        color = Color.White.copy(0.03f),
        startAngle = 180f,
        sweepAngle = 180f,
    )

    scale(ArcScales.MEDIUM) {
        drawDashedArc(color = Color.White.copy(0.05f))
    }

    drawContent()

    scale(ArcScales.SMALL) {
        drawArcProgress(
            progress = progress.coerceAtMost(1f),
            startAngle = ArcConfig.START_ANGLE,
            sweepAngle = ArcConfig.SWEEP_ANGLE,
        )
    }

    scale(ArcScales.EXTRA_SMALL) {
        drawDashedArc(
            color = Gray60.copy(0.5f),
            spacing = ArcStyle.INNER_DASH_SPACING.dp.toPx(),
            thickness = ArcStyle.INNER_DASH_THICKNESS.dp.toPx(),
        )
    }
}

private fun DrawScope.drawDashedArc(
    thickness: Float = ArcStyle.DASH_THICKNESS.dp.toPx(),
    spacing: Float = ArcStyle.DASH_SPACING.dp.toPx(),
    color: Color = Color.White,
    startAngle: Float = ArcConfig.START_ANGLE,
    sweepAngle: Float = ArcConfig.SWEEP_ANGLE,
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(1f, spacing), 0f)
    val stroke = Stroke(
        width = thickness,
        pathEffect = pathEffect,
        cap = StrokeCap.Round,
    )
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = stroke,
    )
}

private fun DrawScope.drawArcProgress(
    progress: Float,
    thickness: Float = ArcStyle.PROGRESS_THICKNESS.dp.toPx(),
    color: Color = AccentPrimary100,
    startAngle: Float = ArcConfig.START_ANGLE,
    sweepAngle: Float = ArcConfig.SWEEP_ANGLE,
) {
    val strokeCap = StrokeCap.Round
    val useCenter = false

    fun drawStrokeArc(thickness: Float) {
        drawArc(
            brush = Brush.linearGradient(
                colorStops = arrayOf(
                    0f to Purple90.copy(0f),
                    0.3f to Purple90.copy(0f),
                    0.6f to Purple90.copy(0.5f),
                    1f to Purple90.copy(0.4f),
                ),
            ),
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = useCenter,
            style = Stroke(
                width = thickness + 1.dp.toPx(),
                cap = strokeCap,
            ),
        )
        drawArc(
            color = Gray60,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = useCenter,
            style = Stroke(
                width = thickness,
                cap = strokeCap,
            ),
        )
    }

    drawStrokeArc(thickness = thickness)
    drawBlur(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle * progress,
        useCenter = useCenter,
    )
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle * progress,
        useCenter = useCenter,
        style = Stroke(
            width = thickness,
            cap = StrokeCap.Round,
        ),
    )
}

@Composable
private fun SubscriptionInfoItem(
    title: String,
    value: String,
    highlightedColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .drawBehind {
                drawSubscriptionInfoBackground(highlightedColor)
            }
            .padding(
                vertical = TrackizerTheme.spacing.medium,
                horizontal = TrackizerTheme.spacing.extraSmall,
            ),
    ) {
        AutoResizedText(
            text = title,
            color = Gray40,
            style = TrackizerTheme.typography.headline1,
        )
        Spacer(modifier = Modifier.height(2.dp))
        AnimatedText(
            text = value,
            style = TrackizerTheme.typography.headline2,
        )
    }
}

private fun DrawScope.drawSubscriptionInfoBackground(highlightedColor: Color) {
    val radius = 18.dp.toPx()

    drawRoundRect(
        color = Gray60.copy(0.2f),
        cornerRadius = CornerRadius(radius, radius),
    )

    drawRoundRect(
        brush = Brush.linearGradient(
            colorStops = arrayOf(
                0f to Purple90.copy(0.15f),
                1f to Color.Transparent,
            ),
            end = Offset(
                x = size.width * 0.5f,
                y = size.height,
            ),
        ),
        cornerRadius = CornerRadius(radius, radius),
        style = Stroke(width = 1.dp.toPx()),
    )

    drawLine(
        color = highlightedColor,
        start = Offset(
            x = size.width * 0.3f,
            y = 0f,
        ),
        end = Offset(
            x = size.width * 0.7f,
            y = 0f,
        ),
    )
}

@Preview
@Composable
private fun SubscriptionDashboardPreview() {
    TrackizerTheme {
        SubscriptionDashboard(
            subscriptions = previewSubscriptions,
            monthlyBudget = 50.0,
            onSeeBudgetClick = { },
        )
    }
}

private data class SubscriptionDashboardData(
    val monthlyBillsValue: Double,
    val budgetCommittedPercentage: Float,
    val activeSubs: Int,
    val highestSubsValue: Double,
    val lowestSubsValue: Double,
) {
    companion object {
        fun from(
            subscriptions: List<Subscription>,
            monthlyBudget: Double,
        ): SubscriptionDashboardData {
            val monthlyBillsValue = subscriptions
                .filter { it.reminder || it.firstPayment.month == nowUtc().month }
                .sumOf { it.price.toDouble() }

            val budgetCommittedPercentage = run {
                val budget = (if (monthlyBudget > 0) monthlyBudget else monthlyBillsValue).toFloat()
                val result = monthlyBillsValue.toFloat() / budget
                if (result.isNaN()) 0f else result.coerceAtMost(1f)
            }

            val activeSubs = subscriptions.count { it.reminder }
            val highestSubsValue = subscriptions.maxOfOrNull { it.price.toDouble() } ?: 0.0
            val lowestSubsValue = subscriptions.minOfOrNull { it.price.toDouble() } ?: 0.0

            return SubscriptionDashboardData(
                monthlyBillsValue = monthlyBillsValue,
                budgetCommittedPercentage = budgetCommittedPercentage,
                activeSubs = activeSubs,
                highestSubsValue = highestSubsValue,
                lowestSubsValue = lowestSubsValue,
            )
        }
    }
}

private object ArcConfig {
    const val START_ANGLE = 135f
    const val SWEEP_ANGLE = 270f
    const val ANIMATION_DURATION = 1000
}

private object ArcStyle {
    const val DASH_THICKNESS = 3f
    const val DASH_SPACING = 8f
    const val PROGRESS_THICKNESS = 16f
    const val INNER_DASH_THICKNESS = 6f
    const val INNER_DASH_SPACING = 20f
}

private object ArcScales {
    const val MEDIUM = 0.85f
    const val SMALL = 0.7f
    const val EXTRA_SMALL = 0.55f
    const val CENTER_WIDTH = 0.5f
    const val CENTER_HEIGHT = 0.55f
}
