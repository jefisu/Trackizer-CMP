package com.jefisu.trackizer.di

import com.jefisu.trackizer.core.platform.auth.AuthProvider
import com.jefisu.trackizer.core.platform.auth.IOSGoogleAuthProvider
import com.jefisu.trackizer.feature.auth.di.AuthScope
import org.koin.core.module.Module
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule: Module = module {
    scope<AuthScope> {
        scopedOf(::IOSGoogleAuthProvider) bind AuthProvider::class
    }
}
