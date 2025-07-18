package com.jefisu.trackizer.auth.presentation.login

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.jefisu.trackizer.auth.data.FakeAuthRepository
import com.jefisu.trackizer.feature.auth.domain.usecase.LoginUserUseCase
import com.jefisu.trackizer.feature.auth.domain.usecase.SendResetPasswordLinkUseCase
import com.jefisu.trackizer.feature.auth.domain.validation.EmailValidator
import com.jefisu.trackizer.feature.auth.presentation.login.LoginAction
import com.jefisu.trackizer.feature.auth.presentation.login.LoginViewModel
import com.jefisu.trackizer.testutil.VALID_EMAIL
import com.jefisu.trackizer.testutil.VALID_PASSWORD
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var fakeRepository: FakeAuthRepository

    @BeforeTest
    fun setUp() {
        fakeRepository = FakeAuthRepository()
        viewModel = createLoginViewModel()
    }

    @Test
    fun loginActionShouldToggleLoadingStateFromFalseToTrueToFalse() = runTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            performLoginAction()
            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()
        }
    }

    @Test
    fun resetPasswordActionShouldToggleLoadingStateFromFalseToTrueToFalse() = runTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoadingResetPassword).isFalse()

            performResetPasswordAction()
            assertThat(awaitItem().isLoadingResetPassword).isTrue()
            assertThat(awaitItem().isLoadingResetPassword).isFalse()
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
    fun passwordChangedActionShouldUpdatePasswordState() = runTest {
        viewModel.state.test {
            assertThat(awaitItem().password).isEmpty()

            updatePassword(VALID_PASSWORD)
            assertThat(awaitItem().password).isEqualTo(VALID_PASSWORD)
        }
    }

    private fun createLoginViewModel(): LoginViewModel {
        val emailValidator = EmailValidator()
        val loginUserUseCase = LoginUserUseCase(fakeRepository, emailValidator)
        val sendResetPasswordLinkUseCase =
            SendResetPasswordLinkUseCase(fakeRepository, emailValidator)

        return LoginViewModel(loginUserUseCase, sendResetPasswordLinkUseCase)
    }

    private fun performLoginAction() {
        viewModel.onAction(LoginAction.Login)
    }

    private fun performResetPasswordAction() {
        viewModel.onAction(LoginAction.SendResetPasswordClick)
    }

    private fun updateEmail(email: String) {
        viewModel.onAction(LoginAction.EmailChanged(email))
    }

    private fun updatePassword(password: String) {
        viewModel.onAction(LoginAction.PasswordChanged(password))
    }
}
