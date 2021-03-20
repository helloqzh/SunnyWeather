package com.helloqzh.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.helloqzh.android.logic.dao.LanguageDao
import com.helloqzh.android.logic.dao.LanguageDao.setLanguage

class SunnyWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN: String = "F6FpeVaXQXs9T3A9"
        fun getResourceString(resourceId: Int) = context.resources.getString(resourceId)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        context.setLanguage(LanguageDao.getSavedLanguage())
    }
}