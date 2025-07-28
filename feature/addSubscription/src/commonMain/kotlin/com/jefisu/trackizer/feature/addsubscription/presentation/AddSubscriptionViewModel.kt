package com.jefisu.trackizer.feature.addsubscription.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.MessageManager
import com.jefisu.trackizer.core.ui.NavigationEvent
import com.jefisu.trackizer.core.util.nowUtc
import com.jefisu.trackizer.core.util.onError
import com.jefisu.trackizer.core.util.onSuccess
import com.jefisu.trackizer.core.util.toMoneyValue
import com.jefisu.trackizer.feature.addsubscription.domain.AddSubscriptionMessage
import com.jefisu.trackizer.feature.addsubscription.domain.usecase.AddSubscriptionUseCase
import com.jefisu.trackizer.feature.addsubscription.domain.usecase.GetSubServicesUseCase
import com.jefisu.trackizer.feature.addsubscription.presentation.util.asMessageUi
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
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AddSubscriptionViewModel(
    private val getSubServicesUseCase: GetSubServicesUseCase,
    private val addSubscriptionUseCase: AddSubscriptionUseCase,
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
        val subscription = with(_state.value) {
            if (service == null) {
                MessageManager.showMessage(
                    AddSubscriptionMessage.Error.SubscriptionAddFailed.asMessageUi(),
                )
                return
            }
            Subscription(
                description = description,
                price = price.toMoneyValue(),
                service = service,
                firstPayment = nowUtc(),
                reminder = false,
                id = "",
            )
        }
        viewModelScope.launch {
            addSubscriptionUseCase.execute(subscription)
                .onSuccess { EventManager.sendEvent(NavigationEvent.Up) }
                .onError { MessageManager.showMessage(it.asMessageUi()) }
        }
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
