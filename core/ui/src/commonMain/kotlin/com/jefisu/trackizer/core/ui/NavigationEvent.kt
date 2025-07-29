package com.jefisu.trackizer.core.ui

sealed interface NavigationEvent : Event {
    data object Up : NavigationEvent
}
