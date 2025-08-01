package com.jefisu.trackizer.core.data.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(includes = [RemoteDataSourceModule::class])
@ComponentScan("com.jefisu.trackizer**")
class DataModule {

    @Single
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth
}
