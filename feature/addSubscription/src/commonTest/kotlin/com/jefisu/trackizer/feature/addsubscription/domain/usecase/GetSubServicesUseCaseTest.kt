@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jefisu.trackizer.feature.addsubscription.domain.usecase

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.jefisu.trackizer.testutil.repository.FakeSubServicesRepository
import com.jefisu.trackizer.testutil.subServices
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

class GetSubServicesUseCaseTest {

    private lateinit var getSubServicesUseCase: GetSubServicesUseCase
    private lateinit var fakeRepository: FakeSubServicesRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeSubServicesRepository()
        getSubServicesUseCase = GetSubServicesUseCase(fakeRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun executeShouldReturnServicesFromRepository() = runTest {
        fakeRepository.setServices(subServices)

        getSubServicesUseCase.execute().test {
            assertThat(awaitItem()).isEqualTo(subServices)
            awaitComplete()
        }
    }

    @Test
    fun executeShouldReturnEmptyListWhenRepositoryIsEmpty() = runTest {
        getSubServicesUseCase.execute().test {
            assertThat(awaitItem()).isEmpty()
            awaitComplete()
        }
    }
}
