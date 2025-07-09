package com.jefisu.trackizer.auth.di

import androidx.compose.runtime.Composable
import org.koin.compose.getKoin

class AuthScope

const val AUTH_SCOPE_ID = "AuthScope"

@Composable
fun rememberAuthScope() = getKoin().getOrCreateScope<AuthScope>(scopeId = "AuthScope")
