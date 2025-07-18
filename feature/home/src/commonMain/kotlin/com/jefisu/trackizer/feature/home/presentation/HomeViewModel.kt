package com.jefisu.trackizer.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jefisu.trackizer.core.domain.repository.CategoryRepository
import com.jefisu.trackizer.core.domain.repository.SubscriptionRepository
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val subscriptionRepository: SubscriptionRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    val state = combine(
        subscriptionRepository.subscriptions,
        categoryRepository.categories,
    ) { subscriptions, categories ->
        HomeState(
            subscriptions = subscriptions,
            monthlyBudget = categories.sumOf { it.budget.toDouble() }.toFloat(),
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5.seconds),
        HomeState(),
    )
}
