package com.jefisu.trackizer.home.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jefisu.trackizer.core.designsystem.util.previewCategories
import com.jefisu.trackizer.core.designsystem.util.previewSubscriptions
import com.jefisu.trackizer.testutil.repository.FakeCategoryRepository
import com.jefisu.trackizer.testutil.repository.FakeSubscriptionRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var subscriptionRepository: FakeSubscriptionRepository
    private lateinit var categoryRepository: FakeCategoryRepository

    private val dispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        subscriptionRepository = FakeSubscriptionRepository()
        categoryRepository = FakeCategoryRepository()
        viewModel = HomeViewModel(
            subscriptionRepository = subscriptionRepository,
            categoryRepository = categoryRepository,
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun stateShouldBeEmptyWhenInitial() = runTest {
        viewModel.state.test {
            assertThat(awaitItem()).isEqualTo(HomeState())
        }
    }

    @Test
    fun stateShouldUpdateWhenDataIsAvailable() = runTest {
        val subscription = previewSubscriptions.first().also {
            subscriptionRepository.addSubscription(it)
        }
        val category = previewCategories.first().also {
            categoryRepository.addCategory(it)
        }
        val expectedState = HomeState(
            subscriptions = listOf(subscription),
            monthlyBudget = category.budget,
        )
        viewModel.state.test {
            // skip initial state
            skipItems(1)

            assertThat(awaitItem()).isEqualTo(expectedState)
        }
    }
}
