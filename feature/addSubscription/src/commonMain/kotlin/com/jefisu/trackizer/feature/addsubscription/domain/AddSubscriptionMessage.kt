package com.jefisu.trackizer.feature.addsubscription.domain

import com.jefisu.trackizer.core.util.Message
import com.jefisu.trackizer.feature.addsubscription.domain.validation.PriceValidationError

sealed interface AddSubscriptionMessage : Message {
    sealed interface Error : AddSubscriptionMessage {
        data object SubscriptionAddFailed : Error
        data class InvalidPrice(val error: PriceValidationError) : Error
    }
}
