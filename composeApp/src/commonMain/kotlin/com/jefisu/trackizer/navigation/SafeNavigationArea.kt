package com.jefisu.trackizer.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SafeNavigationArea(
    navController: NavController,
    blockInteractionMillis: Long,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    content()

    Box(
        modifier = modifier
            .fillMaxSize()
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                layout(placeable.width, placeable.height) {
                    navController.currentBackStackEntryFlow
                        .filterNotNull()
                        .distinctUntilChanged()
                        .onEach {
                            placeable.place(0, 0)
                            delay(blockInteractionMillis)
                            placeable.place(placeable.width, placeable.height)
                        }
                        .launchIn(scope)
                }
            }
            .pointerInput(Unit) {},
    )
}
