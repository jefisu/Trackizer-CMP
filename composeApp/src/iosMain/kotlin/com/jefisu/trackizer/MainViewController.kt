package com.jefisu.trackizer

import androidx.compose.ui.window.ComposeUIViewController
import com.jefisu.trackizer.di.initKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

fun mainViewController() = ComposeUIViewController(
    configure = {
        Firebase.initialize()
        initKoin()
    },
) { App() }
