package com.jefisu.trackizer.auth.presentation.register

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.jefisu.trackizer.auth.data.FakeAuthRepository
import com.jefisu.trackizer.feature.auth.domain.usecase.RegisterUserUseCase
import com.jefisu.trackizer.feature.auth.domain.validation.EmailValidator
import com.jefisu.trackizer.feature.auth.domain.validation.PasswordValidator
import com.jefisu.trackizer.feature.auth.presentation.register.RegisterAction
import com.jefisu.trackizer.feature.auth.presentation.register.RegisterViewModel
import com.jefisu.trackizer.feature.auth.presentation.register.util.PasswordSecurityLevel
import com.jefisu.trackizer.testutil.VALID_EMAIL
import com.jefisu.trackizer.testutil.VALID_PASSWORD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var fakeRepository: FakeAuthRepository

    @BeforeTest
    fun setUp() {
        fakeRepository = FakeAuthRepository()
        viewModel = createRegisterViewModel()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun registerActionWithValidDataShouldToggleLoadingStateFromFalseToTrueToFalse() = runTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            performRegisterAction()
            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()
        }
    }

    @Test
    fun emailChangedActionShouldUpdateEmailState() = runTest {
        viewModel.state.test {
            assertThat(awaitItem().email).isEmpty()

            updateEmail(VALID_EMAIL)
            assertThat(awaitItem().email).isEqualTo(VALID_EMAIL)
        }
    }

    @Test
    fun passwordChangedActionShouldUpdatePasswordStateAndCalculateSecurity() = runTest {
        viewModel.state.test {
            assertThat(awaitItem().password).isEmpty()

            updatePassword(VALID_PASSWORD)
            assertThat(awaitItem().password).isEqualTo(VALID_PASSWORD)
            assertThat(
                awaitItem().passwordSecurityLevel,
            ).isEqualTo(PasswordSecurityLevel.STRONG)
        }
    }

    private fun createRegisterViewModel(): RegisterViewModel {
        return RegisterViewModel(
            registerUserUseCase = RegisterUserUseCase(
                repository = fakeRepository,
                emailValidator = EmailValidator(),
                passwordValidator = PasswordValidator(),
            ),
        )
    }

    private fun performRegisterAction() {
        viewModel.onAction(RegisterAction.OnRegisterClick)
    }

    private fun updateEmail(email: String) {
        viewModel.onAction(RegisterAction.EmailChanged(email))
    }

    private fun updatePassword(password: String) {
        viewModel.onAction(RegisterAction.PasswordChanged(password))
    }
}
