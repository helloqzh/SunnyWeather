package com.helloqzh.android.ui.settings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.helloqzh.android.R
import com.helloqzh.android.databinding.ActivitySettingsBinding
import com.helloqzh.android.logic.dao.LanguageDao.getResourceString
import com.helloqzh.android.logic.model.Language

class SettingsActivity : AppCompatActivity(), LangSettingsDialogFragment.NoticeDialogListener {
    val viewModel by lazy { ViewModelProvider(this).get(SettingsViewModel::class.java) }
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var selectedLang: Language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedLang = viewModel.getSavedLanguage()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.settingsToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }
        binding.settingsSelectedLang.text = selectedLang.getResourceString()
        binding.settingsLangLayout.setOnClickListener {
            LangSettingsDialogFragment(selectedLang).show(supportFragmentManager, "lang")
        }

        viewModel.languageLiveData.observe(this) {
            viewModel.saveLanguage(it)
            binding.settingsSelectedLang.text = selectedLang.getResourceString()
            Toast.makeText(this, "save language.", Toast.LENGTH_SHORT).show()
        }
    }

    fun onLangRadioClicked(view: View) {
        if (view is RadioButton) {
            when (view.id) {
                R.id.settings_lang_en -> selectedLang = Language.English
                R.id.settings_lang_ja -> selectedLang = Language.Japanese
                R.id.settings_lang_zh -> selectedLang = Language.Chinese
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        viewModel.setLanguage(selectedLang)
    }
}