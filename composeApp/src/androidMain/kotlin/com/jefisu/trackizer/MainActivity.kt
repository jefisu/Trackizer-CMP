package com.jefisu.trackizer

import android.app.Activity
import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.jefisu.trackizer.core.platform.ui.AndroidFacebookUIClient
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {

    private val facebookUIClient by lazy { AndroidFacebookUIClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        getKoin().declare(facebookUIClient)
        setContent {
            LightSystemBar()
            App()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller,
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        facebookUIClient.callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Composable
private fun LightSystemBar() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = false
                isAppearanceLightNavigationBars = false
            }
        }
    }
}
