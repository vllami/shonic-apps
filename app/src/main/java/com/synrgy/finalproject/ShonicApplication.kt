package com.synrgy.finalproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShonicApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}