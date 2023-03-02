package com.tasks.firebasecrashlytics

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.CustomKeysAndValues
import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirebaseLogger(private var context: Context) {
    private val analytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(context)
    }

    private val crashlytics: FirebaseCrashlytics by lazy {
        FirebaseCrashlytics.getInstance()
    }

    fun logEvent(eventName: String, parameters: Map<String, Any>) {
        val bundle = Bundle()
        for ((key, value) in parameters) {
            when (value) {
                is String -> bundle.putString(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                else -> {
                    Log.w(TAG, "Unsupported data type: ${value.javaClass.simpleName}")
                }
            }
        }
        analytics.logEvent(eventName, bundle)
    }

    fun logException(throwable: Throwable, parameters: Map<String, Any>) {
        val customKeys = CustomKeysAndValues.Builder()
        for ((key, value) in parameters) {
            when (value) {
                is Int -> customKeys.putInt(key, value)
                is Long -> customKeys.putLong(key, value)
                is Double -> customKeys.putDouble(key, value)
                is Boolean -> customKeys.putBoolean(key, value)
                else -> customKeys.putString(key, value.toString())
            }
        }
        crashlytics.setCustomKeys(customKeys.build())
        crashlytics.recordException(throwable)
    }




}
