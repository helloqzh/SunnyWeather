package com.helloqzh.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.helloqzh.android.logic.WeatherRepository
import com.helloqzh.android.logic.dao.LanguageDao
import com.helloqzh.android.logic.model.City
import com.helloqzh.android.logic.model.Language
import java.util.*

class WeatherViewModel : ViewModel() {
    private val cityLiveData = MutableLiveData<City>()

    var currentCity: City? = null
    var lastUpdateTime: Date? = null

    val weatherLivedata = Transformations.switchMap(cityLiveData) {
        lastUpdateTime = Date()
        WeatherRepository.refreshWeather(it)
    }

    fun refreshWeather(city: City) {
        cityLiveData.value = city
    }

    fun getLanguage(): Language {
        return LanguageDao.getSavedLanguage()
    }
}