package com.jefisu.trackizer.auth.presentation.util

import com.jefisu.trackizer.core.ui.Event

sealed interface AuthEvent : Event {
    data object UserAuthenticated : AuthEvent
}
