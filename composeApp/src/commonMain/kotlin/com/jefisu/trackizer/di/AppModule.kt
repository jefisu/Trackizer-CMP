package com.jefisu.trackizer.di

import com.jefisu.trackizer.core.data.di.DataModule
import com.jefisu.trackizer.feature.addsubscription.di.AddSubscriptionModule
import com.jefisu.trackizer.feature.auth.di.AuthModule
import com.jefisu.trackizer.feature.home.di.HomeModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
    includes = [
        AuthModule::class,
        DataModule::class,
        HomeModule::class,
        AddSubscriptionModule::class,
    ],
)
@ComponentScan(value = ["com.jefisu.trackizer"])
class AppModule
