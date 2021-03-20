package com.helloqzh.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.helloqzh.android.databinding.ActivityMainBinding
import com.helloqzh.android.ui.base.BaseActivity


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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