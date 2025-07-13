package com.jefisu.trackizer.home

import com.jefisu.trackizer.domain.model.Subscription

data class HomeState(
    val monthlyBudget: Float = 0f,
    val subscriptions: List<Subscription> = emptyList(),
)
