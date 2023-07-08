package com.kakao.quokka.preference

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

/**
 * Shared Preference LiveData
 */
abstract class PrefLiveData<T> (
    val sharedPrefs: SharedPreferences,
    val key: String,
    private val defValue: T
) : LiveData<T>() {

    init {
        value = this.getValueFromPreferences(key, defValue)
    }

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (key == this.key) {
            value = getValueFromPreferences(key, defValue)
        }
    }

    abstract fun getValueFromPreferences(key: String, defValue: T): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}

class PrefStringSetLiveData(
    pref: SharedPreferences,
    key: String,
    defValue: Set<String>
) : PrefLiveData<Set<String>>(pref, key, defValue) {
    override fun getValueFromPreferences(
        key: String,
        defValue: Set<String>
    ): Set<String> = sharedPrefs.getStringSet(key, defValue) as Set<String>
}

fun SharedPreferences.stringSetLiveData(
    key: String,
    defValue: Set<String>
): PrefLiveData<Set<String>> {
    return PrefStringSetLiveData(this, key, defValue)
}