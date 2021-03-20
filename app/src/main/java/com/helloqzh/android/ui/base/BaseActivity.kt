package com.helloqzh.android.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.helloqzh.android.SunnyWeatherApplication
import com.helloqzh.android.logic.dao.LanguageDao.setLanguage

open class BaseActivity : AppCompatActivity() {
    val baseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityController.addActivity(this)
        setLanguage()
        baseViewModel.langLiveData.observe(this) {
            setLanguage(it)
            SunnyWeatherApplication.context.setLanguage(it)
            ActivityController.restartApp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

}