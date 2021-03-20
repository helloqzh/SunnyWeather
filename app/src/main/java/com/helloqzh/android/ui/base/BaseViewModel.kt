package com.helloqzh.android.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.helloqzh.android.logic.WeatherRepository
import com.helloqzh.android.logic.model.Language

class BaseViewModel : ViewModel() {
    val langLiveData = MutableLiveData<Language>()

    fun updateUILanguage(language: Language) {
        langLiveData.value = language
    }

    fun saveLanguage(language: Language) = WeatherRepository.saveLanguage(language)

    fun getSavedLanguage() = WeatherRepository.getSavedLanguage()
}