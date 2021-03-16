package com.helloqzh.android.logic.dao

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.google.gson.Gson
import com.helloqzh.android.SunnyWeatherApplication
import com.helloqzh.android.logic.model.City

@Dao
interface CityDao {

    @Query("select * from City where city_ascii like '%' || :query || '%' collate nocase or country like '%' || :query || '%' collate nocase or admin_name like '%' || :query || '%' collate nocase")
    fun searchCities(query: String): LiveData<List<City>>

}

object SharedCityDao {
    private const val SHARED_KEY = "sunny_weather"
    private const val SHARED_CITY_KEY = "city"
    private val gson = Gson()

    fun saveCity(city: City) {
        sharedPreferences().edit().putString(SHARED_CITY_KEY, gson.toJson(city)).apply()
    }

    fun getSavedCity(): City {
        val cityJson = sharedPreferences().getString(SHARED_CITY_KEY, "")
        return gson.fromJson(cityJson, City::class.java)
    }

    fun isCitySaved() = sharedPreferences().contains(SHARED_CITY_KEY)

    private fun sharedPreferences() = SunnyWeatherApplication.context.getSharedPreferences(SHARED_KEY, Context.MODE_PRIVATE)
}