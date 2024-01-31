package com.best.ninth.app.ninthwallpaper

import android.app.Application

class App : Application() {
    private val TAG = "App"

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if(ClockUtils.black_uuid.isEmpty()){
            ClockUtils.black_uuid = ClockUtils.getUUID()
        }
    }
}