package com.jefisu.trackizer.auth.presentation.login

import com.jefisu.trackizer.core.ui.Event

sealed interface LoginEvent : Event {
    data object DismissForgotPasswordBottomSheet : LoginEvent
}
