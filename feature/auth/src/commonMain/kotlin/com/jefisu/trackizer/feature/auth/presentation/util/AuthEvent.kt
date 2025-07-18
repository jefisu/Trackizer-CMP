package com.jefisu.trackizer.feature.auth.presentation.util

import com.jefisu.trackizer.core.ui.Event

sealed interface AuthEvent : Event {
    data object UserAuthenticated : AuthEvent
}
