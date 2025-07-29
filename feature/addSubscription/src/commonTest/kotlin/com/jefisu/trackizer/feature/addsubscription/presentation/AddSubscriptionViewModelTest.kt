@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jefisu.trackizer.feature.addsubscription.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jefisu.trackizer.core.ui.EventManager
import com.jefisu.trackizer.core.ui.NavigationEvent
import com.jefisu.trackizer.feature.addsubscription.domain.usecase.AddSubscriptionUseCase
import com.jefisu.trackizer.feature.addsubscription.domain.usecase.GetSubServicesUseCase
import com.jefisu.trackizer.feature.addsubscription.domain.validation.PriceValidator
import com.jefisu.trackizer.testutil.repository.FakeSubServicesRepository
import com.jefisu.trackizer.testutil.repository.FakeSubscriptionRepository
import com.jefisu.trackizer.testutil.subServices
import com.jefisu.trackizer.testutil.validSubscription
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class AddSubscriptionViewModelTest {

    private lateinit var viewModel: AddSubscriptionViewModel
    private lateinit var fakeSubServicesRepository: FakeSubServicesRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeSubServicesRepository = FakeSubServicesRepository()
        val getSubServicesUseCase = GetSubServicesUseCase(fakeSubServicesRepository)
        val addSubscriptionUseCase = AddSubscriptionUseCase(
            repository = FakeSubscriptionRepository(),
            priceValidator = PriceValidator(),
        )
        viewModel = AddSubscriptionViewModel(
            getSubServicesUseCase = getSubServicesUseCase,
            addSubscriptionUseCase = addSubscriptionUseCase,
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun stateShouldBeInitializedWithAvailableServices() = runTest {
        fakeSubServicesRepository.setServices(subServices)
        viewModel.state.test {
            skipItems(1) // skip initial state
            assertThat(awaitItem().servicesAvailable).isEqualTo(subServices)
        }
    }

    @Test
    fun updateDescriptionShouldUpdateState() = runTest {
        val newDescription = "New Description"
        viewModel.onAction(AddSubscriptionAction.UpdateDescription(newDescription))
        viewModel.state.test {
            skipItems(1) // skip initial state
            assertThat(awaitItem().description).isEqualTo(newDescription)
        }
    }

    @Test
    fun updatePriceShouldUpdateState() = runTest {
        val newPrice = "49.99"
        viewModel.onAction(AddSubscriptionAction.UpdatePrice(newPrice))
        viewModel.state.test {
            skipItems(1) // skip initial state
            assertThat(awaitItem().price).isEqualTo(newPrice)
        }
    }

    @Test
    fun updateSubscriptionServiceShouldUpdateState() = runTest {
        val service = subServices.first()
        viewModel.onAction(AddSubscriptionAction.UpdateSubscriptionService(service))
        viewModel.state.test {
            skipItems(1) // skip initial state
            assertThat(awaitItem().service).isEqualTo(service)
        }
    }

    @Test
    fun addSubscriptionShouldSendNavigateUpEventOnSuccess() = runTest {
        viewModel.onAction(AddSubscriptionAction.UpdateDescription(validSubscription.description))
        viewModel.onAction(AddSubscriptionAction.UpdatePrice("1"))
        viewModel.onAction(
            AddSubscriptionAction.UpdateSubscriptionService(validSubscription.service),
        )
        viewModel.onAction(AddSubscriptionAction.AddSubscription)

        EventManager.events.test {
            assertThat(awaitItem()).isEqualTo(NavigationEvent.Up)
        }
    }
}
