package com.jefisu.trackizer.feature.auth.presentation.thirdpartyauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.trackizer.core.platform.auth.AuthProviderType
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.MessageManager
import com.jefisu.trackizer.core.util.onError
import com.jefisu.trackizer.core.util.onSuccess
import com.jefisu.trackizer.feature.auth.di.AuthScope
import com.jefisu.trackizer.feature.auth.domain.AuthRepository
import com.jefisu.trackizer.feature.auth.presentation.util.AuthEvent
import com.jefisu.trackizer.feature.auth.presentation.util.asMessageUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Scope

@Scope(AuthScope::class)
@KoinViewModel
class ThirdPartyAuthViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ThirdPartyAuthState())
    val state = _state.asStateFlow()

    fun onAction(action: ThirdPartyAuthAction) {
        when (action) {
            is ThirdPartyAuthAction.OnProviderClick -> signInWithProvider(action.type)
            else -> Unit
        }
    }

    private fun signInWithProvider(type: AuthProviderType) {
        viewModelScope.launch {
            _state.update { it.copy(providerLoading = type) }
            repository.signInProvider(type)
                .onSuccess { EventManager.sendEvent(AuthEvent.UserAuthenticated) }
                .onError { MessageManager.showMessage(it.asMessageUi()) }
            _state.update { it.copy(providerLoading = null) }
        }
    }
}
