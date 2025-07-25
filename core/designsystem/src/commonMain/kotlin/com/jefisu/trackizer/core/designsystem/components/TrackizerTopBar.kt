@file:OptIn(ExperimentalMaterial3Api::class)

package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jefisu.trackizer.core.designsystem.Gray30
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import trackizer.core.designsystem.generated.resources.Res
import trackizer.core.designsystem.generated.resources.ic_back
import trackizer.core.designsystem.generated.resources.ic_settings

@Composable
fun TrackizerTopBar(
    title: String?,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TrackizerTopBarDefaults.colors,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            if (title != null) {
                Text(
                    text = title,
                    style = TrackizerTheme.typography.bodyLarge,
                )
            }
        },
        navigationIcon = navigationIcon,
        actions = actions,
        colors = colors,
        scrollBehavior = scrollBehavior,
        modifier = modifier,
    )
}

@Preview
@Composable
fun TrackizerTopBarPreview() {
    TrackizerTheme {
        TrackizerTopBar(
            title = "Trackizer",
            actions = {
                TrackizerTopBarDefaults.SettingsIcon(
                    onClick = {},
                )
            },
        )
    }
}

object TrackizerTopBarDefaults {

    val colors: TopAppBarColors
        @Composable
        get() = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            titleContentColor = Gray30,
            actionIconContentColor = Gray30,
            navigationIconContentColor = Gray30,
        )

    @Composable
    fun SettingsIcon(onClick: () -> Unit, modifier: Modifier = Modifier) {
        IconButton(
            onClick = onClick,
            modifier = modifier,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_settings),
                contentDescription = "Settings icon",
            )
        }
    }

    @Composable
    fun BackIcon(onClick: () -> Unit, modifier: Modifier = Modifier) {
        IconButton(
            onClick = onClick,
            modifier = modifier,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_back),
                contentDescription = "Back icon",
            )
        }
    }
}
