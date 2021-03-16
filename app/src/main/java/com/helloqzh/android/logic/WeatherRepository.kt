package com.helloqzh.android.logic

import androidx.lifecycle.liveData
import com.helloqzh.android.logic.dao.SharedCityDao
import com.helloqzh.android.logic.model.City
import com.helloqzh.android.logic.model.Weather
import com.helloqzh.android.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

object WeatherRepository {

    fun refreshWeather(city: City) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(city)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(city)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                        RuntimeException(
                                "realtime response status is ${realtimeResponse.status}" +
                                        " daily response status is ${dailyResponse.status}"
                        )
                )
            }
        }
    }

    fun saveCity(city: City) = SharedCityDao.saveCity(city)

    fun getSavedCity() = SharedCityDao.getSavedCity()

    fun isCitySaved() = SharedCityDao.isCitySaved()

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>)
    = liveData<Result<T>>(context) {
        val result = try {
            block()
        } catch (e: Exception) {
            Result.failure<T>(e)
        }
        emit(result)
    }
}