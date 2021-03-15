package com.helloqzh.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.helloqzh.android.logic.WeatherRepository
import com.helloqzh.android.logic.model.City

class WeatherViewModel : ViewModel() {
    private val cityLiveData = MutableLiveData<City>()

    var currentCity: City? = null

    val weatherLivedata = Transformations.switchMap(cityLiveData) {
        WeatherRepository.refreshWeather(it)
    }

    fun refreshWeather(city: City) {
        cityLiveData.value = city
    }
}