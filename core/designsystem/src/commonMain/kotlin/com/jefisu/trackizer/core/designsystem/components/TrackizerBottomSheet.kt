package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.core.ModalBottomSheet
import com.composables.core.ModalBottomSheetState
import com.composables.core.Scrim
import com.composables.core.Sheet
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.Gray80
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.util.applyPlatformSpecific

@Composable
fun TrackizerBottomSheet(
    sheetState: ModalBottomSheetState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheet(
        state = sheetState,
        onDismiss = onDismiss,
    ) {
        Scrim(
            enter = fadeIn(),
            exit = fadeOut(),
        )
        Sheet(
            modifier = modifier
                .background(Gray80, RoundedCornerShape(32.dp, 32.dp))
                .imePadding(),
        ) {
            Column(
                horizontalAlignment = horizontalAlignment,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = TrackizerTheme.spacing.extraMedium)
                    .applyPlatformSpecific(
                        android = { padding(bottom = TrackizerTheme.spacing.extraSmall) },
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = TrackizerTheme.spacing.medium)
                        .height(5.dp)
                        .width(40.dp)
                        .background(Gray50, CircleShape)
                        .align(Alignment.CenterHorizontally),
                )
                content()
            }
        }
    }
}
