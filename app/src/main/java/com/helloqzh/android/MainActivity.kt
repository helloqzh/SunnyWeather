package com.helloqzh.android

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.helloqzh.android.databinding.ActivityMainBinding
import com.helloqzh.android.logic.dao.LanguageDao
import com.helloqzh.android.logic.dao.LanguageDao.setLanguage
import com.helloqzh.android.logic.dao.LanguageDao.toLocale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLanguage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> Toast.makeText(this, "You clicked Settings.", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}