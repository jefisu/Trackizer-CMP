package com.jefisu.trackizer.core.ui

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface UiEvent

object UiEventManager {

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    suspend fun sendEvent(uiEvent: UiEvent) {
        _events.emit(uiEvent)
    }
}
