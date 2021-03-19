package com.helloqzh.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.helloqzh.android.logic.dao.LanguageDao
import com.helloqzh.android.logic.dao.LanguageDao.toLocale
import com.helloqzh.android.logic.model.Language

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

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}