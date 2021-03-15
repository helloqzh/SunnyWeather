package com.helloqzh.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN: String = "F6FpeVaXQXs9T3A9"

        fun getString(id: Int): String {
            return context.resources.getString(id)
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}