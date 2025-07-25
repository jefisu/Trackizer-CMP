@file:OptIn(ExperimentalMaterial3Api::class)

package com.jefisu.trackizer.feature.addsubscription.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jefisu.trackizer.core.designsystem.Gray70
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.components.ButtonType
import com.jefisu.trackizer.core.designsystem.components.CurrencyTextField
import com.jefisu.trackizer.core.designsystem.components.TrackizerButton
import com.jefisu.trackizer.core.designsystem.components.TrackizerTextField
import com.jefisu.trackizer.core.designsystem.components.TrackizerTopBar
import com.jefisu.trackizer.core.designsystem.components.TrackizerTopBarDefaults
import com.jefisu.trackizer.core.designsystem.util.previewSubscriptionServices
import com.jefisu.trackizer.core.domain.model.SubscriptionService
import com.jefisu.trackizer.core.ui.getEndlessItem
import com.jefisu.trackizer.core.ui.rememberEndlessPagerState
import com.jefisu.trackizer.core.ui.sphericalPagerAnimation
import com.jefisu.trackizer.core.util.applyPlatformSpecific
import com.jefisu.trackizer.feature.addsubscription.presentation.components.SubServicePageItem
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import trackizer.core.designsystem.generated.resources.Res as DesignSystemRes
import trackizer.core.designsystem.generated.resources.description
import trackizer.feature.addsubscription.generated.resources.Res
import trackizer.feature.addsubscription.generated.resources.add_new_subscription
import trackizer.feature.addsubscription.generated.resources.add_this_platform
import trackizer.feature.addsubscription.generated.resources.monthly_price
import trackizer.feature.addsubscription.generated.resources.new_title

@Composable
fun AddSubscriptionRoot(
    onBackNavigate: () -> Unit,
    viewModel: AddSubscriptionViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val action = action@{ action: AddSubscriptionAction ->
        if (action is AddSubscriptionAction.OnBackClick) {
            onBackNavigate()
            return@action
        }
        viewModel.onAction(action)
    }
    val previewState = state.copy(
        servicesAvailable = previewSubscriptionServices,
    )

    AddSubscriptionScreen(
        state = previewState,
        onAction = action,
    )
}

@Composable
private fun AddSubscriptionScreen(
    state: AddSubscriptionState,
    onAction: (AddSubscriptionAction) -> Unit,
) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            AddSubscriptionTopBar(
                onBackClick = { onAction(AddSubscriptionAction.OnBackClick) },
            )
        },
    ) { innerPadding ->
        AddSubscriptionContent(
            modifier = Modifier.padding(innerPadding),
            state = state,
            onAction = onAction,
        )
    }
}

@Composable
private fun AddSubscriptionTopBar(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    TrackizerTopBar(
        modifier = modifier,
        title = stringResource(Res.string.new_title),
        colors = TrackizerTopBarDefaults.colors.copy(
            containerColor = Gray70,
        ),
        navigationIcon = {
            TrackizerTopBarDefaults.BackIcon(onClick = onBackClick)
        },
    )
}

@Composable
fun AddSubscriptionContent(
    state: AddSubscriptionState,
    onAction: (AddSubscriptionAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(Gray70),
        ) {
            Text(
                text = stringResource(Res.string.add_new_subscription),
                style = TrackizerTheme.typography.headline7,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = TrackizerTheme.spacing.large),
            )
            Spacer(Modifier.height(56.dp))
            ServicePagerWithSelection(
                services = state.servicesAvailable,
                onServiceSelected = { service ->
                    onAction(AddSubscriptionAction.UpdateSubscriptionService(service))
                },
                modifier = Modifier
                    .padding(bottom = 40.dp),
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .heightIn(max = 280.dp)
                .weight(1f)
                .padding(
                    start = TrackizerTheme.spacing.extraMedium,
                    end = TrackizerTheme.spacing.extraMedium,
                    top = TrackizerTheme.spacing.extraMedium,
                )
                .applyPlatformSpecific(
                    android = {
                        padding(bottom = TrackizerTheme.spacing.medium)
                    },
                ),
        ) {
            TrackizerTextField(
                text = state.description,
                onTextChange = { onAction(AddSubscriptionAction.UpdateDescription(it)) },
                fieldName = stringResource(DesignSystemRes.string.description),
                horizontalAlignment = Alignment.CenterHorizontally,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Down)
                },
            )
            CurrencyTextField(
                text = state.price,
                onTextChange = { onAction(AddSubscriptionAction.UpdatePrice(it)) },
                label = stringResource(Res.string.monthly_price),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                },
            )
            TrackizerButton(
                text = stringResource(Res.string.add_this_platform),
                type = ButtonType.Primary,
                onClick = { onAction(AddSubscriptionAction.AddSubscription) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ServicePagerWithSelection(
    services: List<SubscriptionService>,
    onServiceSelected: (SubscriptionService) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberEndlessPagerState(startPage = Int.MAX_VALUE / 2)

    require(services.isNotEmpty()) {
        "servicesAvailable should not be empty"
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            val service = services.getEndlessItem(page)
            onServiceSelected(service)
        }
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 100.dp),
        modifier = modifier,
    ) { page ->
        SubServicePageItem(
            service = services.getEndlessItem(page),
            modifier = Modifier
                .fillMaxWidth()
                .sphericalPagerAnimation(
                    pagerState = pagerState,
                    page = page,
                ),
        )
    }
}

@Preview
@Composable
private fun AddSubscriptionScreenPreview() {
    TrackizerTheme {
        AddSubscriptionScreen(
            state = AddSubscriptionState(
                servicesAvailable = previewSubscriptionServices,
            ),
            onAction = {},
        )
    }
}
