package com.jefisu.trackizer.feature.addsubscription.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.trackizer.feature.addsubscription.domain.usecase.GetSubServicesUseCase
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddSubscriptionViewModel(
    private val getSubServicesUseCase: GetSubServicesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AddSubscriptionState())
    val state = _state
        .onStart {
            loadSubServices()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5.seconds),
            AddSubscriptionState(),
        )

    fun onAction(action: AddSubscriptionAction) {
        when (action) {
            is AddSubscriptionAction.UpdateDescription -> {
                _state.update { it.copy(description = action.description) }
            }

            is AddSubscriptionAction.UpdatePrice -> {
                _state.update { it.copy(price = action.price) }
            }

            is AddSubscriptionAction.UpdateSubscriptionService -> {
                _state.update { it.copy(service = action.service) }
            }

            AddSubscriptionAction.AddSubscription -> addSubscription()
            else -> Unit
        }
    }

    private fun addSubscription() {
        TODO("Not yet implemented")
    }

    private fun loadSubServices() {
        getSubServicesUseCase.execute()
            .distinctUntilChanged()
            .onEach { subServices ->
                _state.update { it.copy(servicesAvailable = subServices) }
            }
            .launchIn(viewModelScope)
    }
}
