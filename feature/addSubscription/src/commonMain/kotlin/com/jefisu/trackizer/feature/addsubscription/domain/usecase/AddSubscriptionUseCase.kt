package com.jefisu.trackizer.feature.addsubscription.domain.usecase

import com.jefisu.trackizer.core.domain.model.Subscription
import com.jefisu.trackizer.core.domain.repository.SubscriptionRepository
import com.jefisu.trackizer.core.util.Result
import com.jefisu.trackizer.feature.addsubscription.domain.AddSubscriptionMessage
import com.jefisu.trackizer.feature.addsubscription.domain.validation.PriceValidator
import org.koin.core.annotation.Single

@Single
class AddSubscriptionUseCase(
    private val repository: SubscriptionRepository,
    private val priceValidator: PriceValidator,
) {
    suspend fun execute(subscription: Subscription): Result<Unit, AddSubscriptionMessage> {
        priceValidator
            .validate(subscription.price)
            .takeIf { !it.successfully }
            ?.let { return Result.Error(AddSubscriptionMessage.Error.InvalidPrice(it.error!!)) }

        return repository.addSubscription(subscription).fold(
            onSuccess = { Result.Success(it) },
            onFailure = { Result.Error(AddSubscriptionMessage.Error.SubscriptionAddFailed) },
        )
    }
}
