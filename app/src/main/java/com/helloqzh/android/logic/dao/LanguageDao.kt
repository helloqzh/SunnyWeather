package com.helloqzh.android.logic.dao

import android.content.Context
import android.os.Build
import android.os.LocaleList
import com.helloqzh.android.R
import com.helloqzh.android.SunnyWeatherApplication
import com.helloqzh.android.logic.model.Language
import java.util.*

object LanguageDao {
    private const val SHARED_KEY = "sunny_weather"
    private const val SHARED_LANG_KEY = "language"

    fun saveLanguage(lang: Language) {
        sharedPreferences().edit().putString(SHARED_LANG_KEY, lang.name).apply()
    }

    fun getSavedLanguage(): Language {
        val lang = sharedPreferences().getString(SHARED_LANG_KEY, null)
        return lang?.let {
            Language.valueOf(lang)
        } ?: getDeviceLanguage()
    }

    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)

    private fun getDeviceLanguage(): Language {
        val lang = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            SunnyWeatherApplication.context.resources.configuration.locales[0].toLang()
        } else SunnyWeatherApplication.context.resources.configuration.locale.toLang()
        saveLanguage(lang)
        return lang
    }

    fun Locale.toLang(): Language {
        return when {
            this.country.startsWith("zh") -> Language.Chinese
            this.country.startsWith("ja") -> Language.Japanese
            else -> Language.English
        }
    }

    fun Language.toLocale(): Locale {
        return when(this) {
            Language.English -> Locale.ENGLISH
            Language.Japanese -> Locale.JAPANESE
            Language.Chinese -> Locale.SIMPLIFIED_CHINESE
        }
    }

    fun Language.getResourceString(): String {
        return when(this) {
            Language.Chinese -> SunnyWeatherApplication.getResourceString(R.string.lang_zh)
            Language.Japanese -> SunnyWeatherApplication.getResourceString(R.string.lang_ja)
            Language.English -> SunnyWeatherApplication.getResourceString(R.string.lang_en)
        }
    }

    fun Language.valueOfApiParameter(): String {
        return when(this) {
            Language.English -> "en_US"
            Language.Japanese -> "ja"
            Language.Chinese -> "zh_CN"
        }
    }

    fun Context.setLanguage(language: Language? = null) {
        val resources = this.resources
        val config = resources.configuration
        val lang = language ?: getSavedLanguage()
        val newLocale = lang.toLocale()
        config.setLocale(newLocale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocales(LocaleList(newLocale))
        }
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}