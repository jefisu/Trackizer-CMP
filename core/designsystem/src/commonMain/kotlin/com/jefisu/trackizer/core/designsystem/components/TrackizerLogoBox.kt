@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.ui.SharedTransitionKeys
import com.jefisu.trackizer.core.ui.sharedTransition
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import trackizer.core.designsystem.generated.resources.Res
import trackizer.core.designsystem.generated.resources.app_logo_no_background
import trackizer.core.designsystem.generated.resources.app_name

@Composable
fun TrackizerLogoBox(
    modifier: Modifier = Modifier,
    logo: (@Composable () -> Unit)? = null,
    content: (@Composable BoxScope.() -> Unit)? = null,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (logo == null) {
            TrackizerLogoDefaults.Logo(
                modifier = Modifier
                    .padding(top = 90.dp)
                    .align(Alignment.TopCenter)
                    .sharedTransition {
                        Modifier.sharedElement(
                            sharedContentState = rememberSharedContentState(
                                SharedTransitionKeys.APP_LOGO,
                            ),
                            animatedVisibilityScope = it,
                        )
                    },
            )
        } else {
            logo()
        }
        content?.invoke(this)
    }
}

@Preview
@Composable
private fun TrackizerLogoBoxPreview() {
    TrackizerTheme {
        TrackizerLogoBox()
    }
}

object TrackizerLogoDefaults {

    @Composable
    fun Logo(modifier: Modifier = Modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Image(
                painter = painterResource(Res.drawable.app_logo_no_background),
                contentDescription = null,
                modifier = Modifier.size(TrackizerTheme.size.appLogoSmall),
            )
            Spacer(modifier = Modifier.width(TrackizerTheme.spacing.extraSmall))
            Text(
                text = stringResource(Res.string.app_name).uppercase(),
                style = TrackizerTheme.typography.headline5,
            )
        }
    }
}
