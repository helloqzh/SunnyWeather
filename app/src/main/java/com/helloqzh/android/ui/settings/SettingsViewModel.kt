package com.helloqzh.android.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.helloqzh.android.logic.WeatherRepository
import com.helloqzh.android.logic.model.Language

class SettingsViewModel : ViewModel() {
    val languageLiveData = MutableLiveData<Language>()

    fun setLanguage(lang: Language) {
        languageLiveData.value = lang
    }

    fun saveLanguage(lang: Language) {
        WeatherRepository.saveLanguage(lang)
    }

    fun getSavedLanguage(): Language {
        return WeatherRepository.getSavedLanguage()
    }

}