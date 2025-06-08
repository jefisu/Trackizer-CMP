package com.jefisu.trackizer.auth.presentation.login

import com.jefisu.trackizer.core.ui.UiEvent

sealed interface LoginEvent : UiEvent {
    data object DismissForgotPasswordBottomSheet : LoginEvent
}
