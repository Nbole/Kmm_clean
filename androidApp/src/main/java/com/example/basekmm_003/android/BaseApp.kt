package com.example.basekmm_003.android

import android.app.Application
import com.example.basekmm_003.di.initKoin
import org.koin.android.ext.koin.androidContext

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApp)
        }
    }
}