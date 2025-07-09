package com.jefisu.trackizer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.trackizer.auth.di.AUTH_SCOPE_ID
import com.jefisu.trackizer.core.ui.MessageManager
import com.jefisu.trackizer.core.util.closeKoinScope
import com.jefisu.trackizer.domain.UserRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AppViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = combine(
        _state,
        MessageManager.message,
    ) { state, message ->
        state.copy(
            message = message,
        )
    }.onStart {
        verifyUserAuthenticated()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5.seconds),
        AppState(),
    )

    fun dismissMessage() = MessageManager.closeMessage()

    private fun verifyUserAuthenticated() {
        viewModelScope.launch {
            val state = _state.updateAndGet {
                it.copy(loggedIn = userRepository.isAuthenticated())
            }
            if (state.loggedIn) {
                closeKoinScope(AUTH_SCOPE_ID)
            }
        }
    }
}
