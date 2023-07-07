package com.kakao.quokka.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.kakao.quokka.ext.currMillis
import com.kakao.quokka.ext.splitKey
import javax.inject.Inject


class PrefManagerImpl @Inject constructor(
    private val context: Context,
) : PrefManager {

    override val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    /**
     * Simple String Set
     */
    override fun setStringSet(key: String, value: Set<String>) {
        preferences.edit().putStringSet(key, value).apply()
    }

    /**
     * Simple String Get
     */
    override fun getStringSet(key: String): Set<String> {
        return preferences.getStringSet(key, setOf()) as Set<String>
    }

    /**
     * Update String Set
     * @desc prevent duplicate value
     * @desc format 'keyword||millis'
     * @example '검색어||1688719919529'
     */
    override fun updateStringSet(key: String, value: String) {
        val prefs = getDocList(key)

        val updatedPref = prefs.map { p ->
            val keySet = p.splitKey()
            val _key = keySet.first
            val _time = keySet.second

            if (_key == value) {
                "${value}||${currMillis}"
            } else {
                "${_key}||${_time}"
            }
        }

        setStringSet(key, updatedPref.toSet())
    }

    /**
     * Add String Set
     * @desc format 'keyword||millis'
     * @example '검색어||1688719919529'
     */
    override fun addStringSet(key: String, value: String) {
        val prefs = getDocList(key)
        prefs.add("${value}||${currMillis}")

        setStringSet(key, prefs)
    }

    /**
     * Remove String Set
     * @desc remove string value
     */
    override fun removeStringSet(key: String, value: String) {
        val prefs = getDocList(key)

        var removeTarget = ""
        prefs.forEach { d ->
            try {
                val keySet = d.splitKey()
                val dValue = keySet.first

                if (dValue == value) {
                    removeTarget = d
                    return@forEach
                }
            } catch (e: IndexOutOfBoundsException) {
                return@forEach
            }
        }

        if (removeTarget.isNotBlank()) prefs.remove(removeTarget)
        setStringSet(key, prefs)
    }

    /**
     * Clear Key
     * @desc delete key
     */
    override fun clearKey(key: String) {
        preferences.edit().remove(key).apply()
    }

    private fun getDocList(key: String): MutableSet<String> {
        val prefSet = getStringSet(key)

        return mutableSetOf<String>().also { s ->
            prefSet.forEach { f -> s.add(f) }
        }
    }
}
