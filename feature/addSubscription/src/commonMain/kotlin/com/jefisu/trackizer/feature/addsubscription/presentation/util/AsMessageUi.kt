package com.jefisu.trackizer.feature.addsubscription.presentation.util

import com.jefisu.trackizer.core.ui.MessageUi
import com.jefisu.trackizer.core.ui.UiText
import com.jefisu.trackizer.feature.addsubscription.domain.AddSubscriptionMessage
import com.jefisu.trackizer.feature.addsubscription.domain.validation.PriceValidationError
import trackizer.feature.addsubscription.generated.resources.Res
import trackizer.feature.addsubscription.generated.resources.empty_price_error
import trackizer.feature.addsubscription.generated.resources.subscription_add_failed

fun AddSubscriptionMessage.asMessageUi(): MessageUi {
    return when (this) {
        is AddSubscriptionMessage.Error -> MessageUi.Error(toUiText())
    }
}

private fun AddSubscriptionMessage.Error.toUiText(): UiText = when (this) {
    is AddSubscriptionMessage.Error.InvalidPrice -> error.toUiText()
    AddSubscriptionMessage.Error.SubscriptionAddFailed -> UiText.StringRes(
        Res.string.subscription_add_failed,
    )
}

private fun PriceValidationError.toUiText(): UiText = when (this) {
    PriceValidationError.EMPTY -> UiText.StringRes(Res.string.empty_price_error)
}
