package com.devtech.islamicdictionary.ui

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.devtech.islamicdictionary.R
import dagger.hilt.android.AndroidEntryPoint

import androidx.preference.PreferenceManager
import com.devtech.islamicdictionary.utils.updateLocale
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainContainer).navigateUp()

    override fun attachBaseContext(base: Context) {
        val key = PreferenceManager.getDefaultSharedPreferences(base).getString(base.getString(R.string.key_language), "in")
        val localeToSwitchTo = Locale(key!!)
        val localeUpdatedContext: ContextWrapper = updateLocale(base, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }
}