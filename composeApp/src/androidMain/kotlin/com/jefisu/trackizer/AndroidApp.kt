package com.jefisu.trackizer

import android.app.Application
import com.jefisu.trackizer.di.initKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import org.koin.android.ext.koin.androidContext

class AndroidApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(applicationContext)
        initKoin {
            androidContext(applicationContext)
        }
    }
}
