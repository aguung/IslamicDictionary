package com.devtech.islamicdictionary

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.devtech.islamicdictionary.utils.CrashReportingTree
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class IslamicDictionary : Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(if (BuildConfig.DEBUG) Timber.DebugTree() else CrashReportingTree())
        FirebaseAnalytics.getInstance(this)
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_key_night), false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}