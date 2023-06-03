package com.hcapps.xpenzave

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.appwrite.appwrite.BuildConfig
import timber.log.Timber
import timber.log.Timber.*

@HiltAndroidApp
class XpenzaveApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
    }

}