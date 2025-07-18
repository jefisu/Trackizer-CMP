package com.jefisu.trackizer.feature.home.presentation

import com.jefisu.trackizer.core.domain.model.Subscription

data class HomeState(
    val monthlyBudget: Float = 0f,
    val subscriptions: List<Subscription> = emptyList(),
)
