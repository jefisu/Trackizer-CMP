package com.jefisu.trackizer.auth.presentation.thirdpartyauth

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import com.jefisu.trackizer.auth.data.FakeAuthRepository
import com.jefisu.trackizer.core.platform.auth.AuthProviderType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class ThirdPartyAuthViewModelTest {

    private lateinit var viewModel: ThirdPartyAuthViewModel
    private lateinit var fakeRepository: FakeAuthRepository

    @BeforeTest
    fun setUp() {
        fakeRepository = FakeAuthRepository()
        viewModel = createThirdPartyAuthViewModel()
    }

    @Test
    fun `google provider click should toggle loading state for google provider`() = runTest {
        testProviderLoadingFlow(AuthProviderType.GOOGLE)
    }

    @Test
    fun `facebook provider click should toggle loading state for facebook provider`() = runTest {
        testProviderLoadingFlow(AuthProviderType.FACEBOOK)
    }

    private fun createThirdPartyAuthViewModel(): ThirdPartyAuthViewModel {
        return ThirdPartyAuthViewModel(fakeRepository)
    }

    private fun performProviderClick(providerType: AuthProviderType) {
        viewModel.onAction(ThirdPartyAuthAction.OnProviderClick(providerType))
    }

    private suspend fun testProviderLoadingFlow(providerType: AuthProviderType) {
        viewModel.state.test {
            assertThat(awaitItem().providerLoading).isNull()

            performProviderClick(providerType)
            assertThat(awaitItem().providerLoading).isEqualTo(providerType)
            assertThat(awaitItem().providerLoading).isNull()
        }
    }
}
