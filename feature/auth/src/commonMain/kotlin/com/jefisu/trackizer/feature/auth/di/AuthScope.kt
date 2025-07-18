package com.jefisu.trackizer.feature.auth.di

import androidx.compose.runtime.Composable
import org.koin.compose.getKoin

class AuthScope

const val AUTH_SCOPE_ID = "AuthScope"

@Composable
fun rememberAuthScope() = getKoin().getOrCreateScope<AuthScope>(scopeId = AUTH_SCOPE_ID)
