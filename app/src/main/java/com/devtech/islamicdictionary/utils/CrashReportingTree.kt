package com.devtech.islamicdictionary.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO || priority == Log.WARN || priority == Log.ASSERT) {
            return
        }
        val t = throwable ?: Exception(message)

        FirebaseCrashlytics.getInstance().setCustomKeys {
            key(CRASHLYTICS_KEY_PRIORITY, priority)
            key(CRASHLYTICS_KEY_TAG, tag!!)
            key(CRASHLYTICS_KEY_MESSAGE, message)
        }
        FirebaseCrashlytics.getInstance().log(message)
        FirebaseCrashlytics.getInstance().recordException(t)
    }

    companion object {
        private const val CRASHLYTICS_KEY_PRIORITY = "priority"
        private const val CRASHLYTICS_KEY_TAG = "tag"
        private const val CRASHLYTICS_KEY_MESSAGE = "message"
    }
}
