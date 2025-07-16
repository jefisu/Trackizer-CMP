package com.jefisu.trackizer.core.designsystem.util

import androidx.compose.ui.graphics.Color
import com.jefisu.trackizer.core.designsystem.Microsoft365ContainerColor
import com.jefisu.trackizer.core.designsystem.SpotifyContainerColor
import org.jetbrains.compose.resources.DrawableResource
import trackizer.core.designsystem.generated.resources.Res
import trackizer.core.designsystem.generated.resources.ic_hbo_go
import trackizer.core.designsystem.generated.resources.ic_microsoft_365
import trackizer.core.designsystem.generated.resources.ic_netflix
import trackizer.core.designsystem.generated.resources.ic_spotify
import trackizer.core.designsystem.generated.resources.ic_youtube_premium

enum class PredefinedSubscriptionServices(
    val displayName: String,
    val imageRes: DrawableResource,
    val color: Color,
) {
    YOUTUBE_PREMIUM(
        displayName = "YouTube Premium",
        imageRes = Res.drawable.ic_youtube_premium,
        color = Color.Red,
    ),
    SPOTIFY(
        displayName = "Spotify",
        imageRes = Res.drawable.ic_spotify,
        color = SpotifyContainerColor,
    ),
    MICROSOFT_365(
        displayName = "Microsoft 365",
        imageRes = Res.drawable.ic_microsoft_365,
        color = Microsoft365ContainerColor,
    ),
    NETFLIX(
        displayName = "Netflix",
        imageRes = Res.drawable.ic_netflix,
        color = Color.Black,
    ),
    HBO_GO(
        displayName = "HBO Go",
        imageRes = Res.drawable.ic_hbo_go,
        color = Color.Black,
    ),
}
