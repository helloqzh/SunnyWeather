package com.helloqzh.android.logic.network

import com.helloqzh.android.R
import com.helloqzh.android.SunnyWeatherApplication
import com.helloqzh.android.logic.model.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object SunnyWeatherNetwork {

    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun getDailyWeather(city: City) = weatherService.getDailyWeather(city.lng, city.lat).await()

    suspend fun getRealtimeWeather(city: City) = weatherService.getRealtimeWeather(city.lng, city.lat).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(
                            RuntimeException(SunnyWeatherApplication.getString(R.string.error_request))
                    )
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}