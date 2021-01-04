package com.devtech.islamicdictionary.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build

import android.os.LocaleList
import java.util.*

class ContextUtils(base: Context) : ContextWrapper(base)

fun updateLocale(mContext: Context, localeToSwitchTo: Locale): ContextWrapper {
    var context = mContext
    val resources: Resources = context.resources
    val configuration: Configuration = resources.configuration
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val localeList = LocaleList(localeToSwitchTo)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)
    } else {
        configuration.locale = localeToSwitchTo
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
        context = context.createConfigurationContext(configuration)
    } else {
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
    return ContextUtils(context)
}