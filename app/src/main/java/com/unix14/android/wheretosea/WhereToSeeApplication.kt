package com.unix14.android.wheretosea

import android.app.Application
import com.google.firebase.FirebaseApp
import com.monkeytech.playform.di.appModule
import com.monkeytech.playform.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WhereToSeeApplication : Application(){

    override fun onCreate() {
        super.onCreate()

        setupKoin()
        FirebaseApp.initializeApp(this)

    }

    private fun setupKoin() {
        // start Koin (di) context
        startKoin {
            androidContext(this@WhereToSeeApplication)
            androidLogger()
            modules(appModule, networkModule)
        }
    }
}