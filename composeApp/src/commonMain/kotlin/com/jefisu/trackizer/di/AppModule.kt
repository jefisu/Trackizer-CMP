package com.jefisu.trackizer.di

import com.jefisu.trackizer.auth.di.AuthModule
import com.jefisu.trackizer.data.di.DataModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [AuthModule::class, DataModule::class])
@ComponentScan(value = ["com.jefisu.trackizer"])
class AppModule
