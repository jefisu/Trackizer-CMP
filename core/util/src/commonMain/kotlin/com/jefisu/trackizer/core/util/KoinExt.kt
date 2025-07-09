package com.jefisu.trackizer.core.util

import org.koin.mp.KoinPlatform

fun closeKoinScope(scopeId: String) {
    val scope = KoinPlatform.getKoin().getScopeOrNull(scopeId) ?: return
    scope.close()
}
