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
        lateinit var TOKEN: String
        fun getResourceString(resourceId: Int) = context.resources.getString(resourceId)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        context.setLanguage(LanguageDao.getSavedLanguage())
        TOKEN = context.assets.open("TOKEN").bufferedReader().use { it.readText() }
    }
}