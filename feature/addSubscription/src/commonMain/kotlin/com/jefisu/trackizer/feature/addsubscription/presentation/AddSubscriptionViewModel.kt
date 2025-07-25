package com.jefisu.trackizer.feature.addsubscription.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddSubscriptionViewModel : ViewModel() {

    private val _state = MutableStateFlow(AddSubscriptionState())
    val state = _state.asStateFlow()

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
}
