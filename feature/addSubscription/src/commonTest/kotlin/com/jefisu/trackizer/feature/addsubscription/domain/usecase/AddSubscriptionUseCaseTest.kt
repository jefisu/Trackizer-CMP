@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jefisu.trackizer.feature.addsubscription.domain.usecase

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jefisu.trackizer.core.util.Result
import com.jefisu.trackizer.feature.addsubscription.domain.AddSubscriptionMessage
import com.jefisu.trackizer.feature.addsubscription.domain.validation.PriceValidationError
import com.jefisu.trackizer.feature.addsubscription.domain.validation.PriceValidator
import com.jefisu.trackizer.testutil.invalidPriceSubscription
import com.jefisu.trackizer.testutil.repository.FakeSubscriptionRepository
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

class AddSubscriptionUseCaseTest {

    private lateinit var addSubscriptionUseCase: AddSubscriptionUseCase
    private lateinit var fakeRepository: FakeSubscriptionRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeSubscriptionRepository()
        addSubscriptionUseCase = AddSubscriptionUseCase(
            repository = fakeRepository,
            priceValidator = PriceValidator(),
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun executeShouldReturnSuccessWhenSubscriptionIsAdded() = runTest {
        val result = addSubscriptionUseCase.execute(validSubscription)
        assertThat(result).isEqualTo(Result.Success(Unit))
    }

    @Test
    fun executeShouldReturnInvalidPriceErrorWhenPriceIsInvalid() = runTest {
        val result = addSubscriptionUseCase.execute(invalidPriceSubscription)
        val expectedResult = Result.Error(
            AddSubscriptionMessage.Error.InvalidPrice(PriceValidationError.EMPTY),
        )
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun executeShouldReturnSubscriptionAddFailedErrorWhenRepositoryFails() = runTest {
        fakeRepository.setShouldReturnError()
        val result = addSubscriptionUseCase.execute(validSubscription)
        val expectedResult = Result.Error(AddSubscriptionMessage.Error.SubscriptionAddFailed)
        assertThat(result).isEqualTo(expectedResult)
    }
}
