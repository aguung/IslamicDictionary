package com.devtech.islamicdictionary.ui.setting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.devtech.islamicdictionary.R
import com.devtech.islamicdictionary.utils.toogleActionbar
import kotlinx.coroutines.launch


class SettingFragment : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).toogleActionbar(show = true, back = true, title = "Settings")
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
        val theme = findPreference(getString(R.string.pref_key_night)) as SwitchPreference?
        val language = findPreference(getString(R.string.key_language)) as ListPreference?
        theme?.onPreferenceChangeListener = modeChangeListener
        language?.onPreferenceChangeListener = modeChangeListener

    }

    private val modeChangeListener = Preference.OnPreferenceChangeListener { prefecence, newValue ->
        when (prefecence.key) {
            getString(R.string.pref_key_night) -> {
                if (newValue as Boolean) {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    updateTheme(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            getString(R.string.key_language) -> {
                val old = preferenceManager.sharedPreferences.getString(getString(R.string.key_language), "in")
                if (old != newValue) {
                    restartActivity()
                }
            }
        }
        true
    }

    private fun restartActivity() {
        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }

}