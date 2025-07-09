package com.jefisu.trackizer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.trackizer.core.ui.MessageManager
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AppViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = combine(
        _state,
        MessageManager.message,
    ) { state, message ->
        state.copy(
            message = message,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5.seconds),
        AppState(),
    )

    fun dismissMessage() = MessageManager.closeMessage()
}
