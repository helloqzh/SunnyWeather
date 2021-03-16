package com.helloqzh.android.ui.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.helloqzh.android.R
import com.helloqzh.android.SunnyWeatherApplication
import com.helloqzh.android.databinding.ActivityWeatherBinding
import com.helloqzh.android.logic.model.City
import com.helloqzh.android.logic.model.Weather
import com.helloqzh.android.logic.model.getSky
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    companion object {
        val INTENT_CITY = "SELECTED_CITY"
        private val gson = Gson()
    }

    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (viewModel.currentCity == null) {
            val cityJsonStr = intent.getStringExtra(INTENT_CITY)
            if (!cityJsonStr.isNullOrEmpty()) {
                viewModel.currentCity = gson.fromJson(cityJsonStr, City::class.java)
            }
        }
        viewModel.weatherLivedata.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, R.string.error_get_weather, Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            binding.refreshLayout.isRefreshing = false
        })
        binding.refreshLayout.setColorSchemeResources(R.color.teal_200)
        refreshWeather()
        binding.refreshLayout.setOnRefreshListener { refreshWeather() }
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.currentCity!!)
        binding.refreshLayout.isRefreshing = true
    }

    private fun showWeatherInfo(weather: Weather) {
        binding.now.cityName.text = viewModel.currentCity?.city_ascii
        val realtime = weather.realtime
        val daily = weather.daily

        // now.xml
        with(binding.now) {
            val currentTempText = "${realtime.temperature.toInt()} ℃"
            this.currentTemp.text = currentTempText
            this.currentSky.text = getSky(realtime.skycon).info
            val currentPM25Text = "${SunnyWeatherApplication.getString(R.string.label_AQI)} ${realtime.airQuality.aqi.chn.toInt()}"
            this.currentAQI.text = currentPM25Text
            this.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        }

        // forecase.xml
        with(binding.forecase) {
            this.forecastLayout.removeAllViews()
            val days = daily.skycon.size
            for (i in 0 until days) {
                val skycon = daily.skycon[i]
                val temperature = daily.temperature[i]
                val view = LayoutInflater.from(this@WeatherActivity)
                        .inflate(R.layout.forecast_item, this.forecastLayout, false)
                val dateInfo = view.findViewById<TextView>(R.id.dateInfo)
                val skyIcon = view.findViewById<ImageView>(R.id.skyIcon)
                val skyInfo = view.findViewById<TextView>(R.id.skyInfo)
                val temperatureInfo = view.findViewById<TextView>(R.id.temperatureInfo)
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dateInfo.text = simpleDateFormat.format(skycon.date)
                val sky = getSky(skycon.value)
                skyIcon.setImageResource(sky.icon)
                skyInfo.text = sky.info
                val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
                temperatureInfo.text = tempText
                forecastLayout.addView(view)
            }
        }

        // life_index.xml
        with(binding.lifeIndex) {
            val lifeIndex = daily.lifeIndex
            this.coldRiskText.text = lifeIndex.coldRisk[0].desc
            this.dressingText.text = lifeIndex.dressing[0].desc
            this.ultravioletText.text = lifeIndex.ultraviolet[0].desc
            this.carWashingText.text = lifeIndex.carWashing[0].desc
        }

        binding.weatherLayout.visibility = View.VISIBLE
    }
}