package com.helloqzh.android.ui.city

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.helloqzh.android.logic.dao.AppDatabase
import com.helloqzh.android.logic.model.City

class CityViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val cityList = ArrayList<City>()

    val cityLiveData = Transformations.switchMap(searchLiveData) { query ->
        AppDatabase.getDatabase().cityDao().searchCities(query)
    }

    fun searchCities(query: String) {
        searchLiveData.value = query
    }
}