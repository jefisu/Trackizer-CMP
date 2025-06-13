package com.jefisu.trackizer.core.ui

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface Event

object EventManager {

    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    suspend fun sendEvent(event: Event) {
        _events.emit(event)
    }
}
