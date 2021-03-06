package com.helloqzh.android.ui.weather

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.helloqzh.android.R
import com.helloqzh.android.databinding.ActivityWeatherBinding
import com.helloqzh.android.logic.model.City
import com.helloqzh.android.logic.model.Weather
import com.helloqzh.android.logic.model.getSky
import com.helloqzh.android.ui.base.BaseActivity
import com.helloqzh.android.ui.settings.SettingsActivity
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : BaseActivity() {

    companion object {
        val INTENT_CITY = "SELECTED_CITY"
        private val gson = Gson()
    }

    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView = window.decorView
        @Suppress("DEPRECATION")
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
        binding.drawerLayout.closeDrawers()
        binding.now.navBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.now.navSettings.setOnClickListener {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            startActivity(settingsIntent)
        }
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerClosed(drawerView: View) {
                // hide input keyboard
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(drawerView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }

            override fun onDrawerStateChanged(newState: Int) {}

        })
        viewModel.weatherLivedata.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, R.string.error_get_weather, Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
            val simpleDateFormat = SimpleDateFormat("HH:mm", resources.configuration.locale!!)
            binding.now.lastUpdateTime.text = simpleDateFormat.format(viewModel.lastUpdateTime)
            binding.refreshLayout.isRefreshing = false
        })
        binding.refreshLayout.setColorSchemeResources(R.color.colorPrimary)
        refreshWeather()
        binding.refreshLayout.setOnRefreshListener { refreshWeather() }
    }

    fun refreshWeather() {
        viewModel.refreshWeather(viewModel.currentCity!!)
        binding.refreshLayout.isRefreshing = true
        binding.drawerLayout.closeDrawers()
    }

    private fun showWeatherInfo(weather: Weather) {
        binding.now.cityName.text = viewModel.currentCity?.city_ascii
        val realtime = weather.realtime
        val daily = weather.daily

        // now.xml
        with(binding.now) {
            val currentTempText = "${realtime.temperature.toInt()} ???"
            this.currentTemp.text = currentTempText
            this.currentSky.text = getSky(realtime.skycon, viewModel.getLanguage()).info
            val currentPM25Text = "${resources.getString(R.string.label_AQI)} ${realtime.airQuality.aqi.chn.toInt()}"
            this.currentAQI.text = currentPM25Text
            this.nowLayout.setBackgroundResource(getSky(realtime.skycon, viewModel.getLanguage()).bg)
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
                val simpleDateFormat = SimpleDateFormat("MM-dd(E)", resources.configuration.locale!!)
                dateInfo.text = simpleDateFormat.format(skycon.date)
                val sky = getSky(skycon.value, viewModel.getLanguage())
                skyIcon.setImageResource(sky.icon)
                skyInfo.text = sky.info
                val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ???"
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
            this.pm25Text.text = realtime.airQuality.pm25.toString()
            val humidity = "${realtime.humidity * 100}%"
            this.humidityText.text = humidity
        }

        binding.weatherLayout.visibility = View.VISIBLE
    }
}