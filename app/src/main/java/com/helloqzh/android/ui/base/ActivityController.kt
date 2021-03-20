package com.helloqzh.android.ui.base

import android.app.Activity
import android.content.Intent
import com.google.gson.Gson
import com.helloqzh.android.MainActivity
import com.helloqzh.android.SunnyWeatherApplication
import com.helloqzh.android.logic.WeatherRepository
import com.helloqzh.android.ui.weather.WeatherActivity

object ActivityController {
    private val activities = ArrayList<Activity>()
    private val gson = Gson()
    fun addActivity(activity: Activity) = activities.add(activity)

    fun removeActivity(activity: Activity) = activities.remove(activity)

    fun finishAll() {
        for (activity in activities){
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    fun restartApp() {
        for (activity in activities){
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()

        // restart Main Activity
        val hasSavedCity = WeatherRepository.isCitySaved()
        val intent = if (hasSavedCity) {
            Intent(SunnyWeatherApplication.context, WeatherActivity::class.java)
                .putExtra(WeatherActivity.INTENT_CITY,
                    gson.toJson(WeatherRepository.getSavedCity()))
        } else Intent(SunnyWeatherApplication.context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        SunnyWeatherApplication.context.startActivity(intent)
    }
}