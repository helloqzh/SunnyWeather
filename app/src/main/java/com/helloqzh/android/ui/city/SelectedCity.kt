package com.helloqzh.android.ui.city

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.helloqzh.android.databinding.ActivitySelectedCityBinding

class SelectedCity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedCityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedCityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}