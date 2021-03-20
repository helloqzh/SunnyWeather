package com.helloqzh.android.ui.settings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.helloqzh.android.R
import com.helloqzh.android.databinding.ActivitySettingsBinding
import com.helloqzh.android.logic.dao.LanguageDao.getResourceString
import com.helloqzh.android.logic.model.Language
import com.helloqzh.android.ui.base.BaseActivity

class SettingsActivity : BaseActivity(), LangSettingsDialogFragment.NoticeDialogListener {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var selectedLang: Language

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedLang = baseViewModel.getSavedLanguage()
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
        baseViewModel.saveLanguage(selectedLang)
        baseViewModel.updateUILanguage(selectedLang)
    }
}