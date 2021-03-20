package com.helloqzh.android.ui.settings

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.helloqzh.android.R
import com.helloqzh.android.logic.model.Language

class LangSettingsDialogFragment(private val initLang: Language) : DialogFragment() {
    private lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment) {}
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val settingsLangView = inflater.inflate(R.layout.settings_lang, null)
            setInitLang(settingsLangView)
            builder.setTitle(R.string.settings_lang)
                .setView(settingsLangView)
                .setPositiveButton(R.string.dialog_confirm) { _, _ ->
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton(R.string.dialog_cancel) { _, _ ->
                    listener.onDialogNegativeClick(this)
                }.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    private fun setInitLang(view: View) {
        when (initLang) {
            Language.English -> view.findViewById<RadioButton>(R.id.settings_lang_en).isChecked = true
            Language.Japanese -> view.findViewById<RadioButton>(R.id.settings_lang_ja).isChecked = true
            Language.Chinese -> view.findViewById<RadioButton>(R.id.settings_lang_zh).isChecked = true
        }
    }
}