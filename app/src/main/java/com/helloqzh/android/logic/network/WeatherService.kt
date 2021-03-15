package com.helloqzh.android.logic.network

import com.helloqzh.android.SunnyWeatherApplication
import com.helloqzh.android.logic.model.DailyResponse
import com.helloqzh.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng")lng: String, @Path("lat")lat: String, @Query("lang")lang: String = "en_US"): Call<RealtimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng: String, @Path("lat")lat: String, @Query("lang")lang: String = "en_US"): Call<DailyResponse>

}