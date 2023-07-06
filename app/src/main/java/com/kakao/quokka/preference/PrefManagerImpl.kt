package com.kakao.quokka.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.kakao.quokka.constants.QkConstants
import com.kakao.quokka.ext.currMillis
import com.kakao.quokka.ext.splitKey
import javax.inject.Inject


class PrefManagerImpl @Inject constructor(
    private val context: Context,
) : PrefManager {

    override val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)

    override fun setStringSet(key: String, value: Set<String>) {
        preferences.edit().putStringSet(key, value).apply()
    }

    override fun getStringSet(key: String): Set<String> {
        return preferences.getStringSet(key, setOf()) as Set<String>
    }

    override fun addStringSet(key: String, value: String) {
        val prefs = getDocList(key)
        prefs.add("${value}||${currMillis}")

        setStringSet(key, prefs)
    }

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

    private fun getDocList(key: String): MutableSet<String> {
        val prefSet = getStringSet(key)

        return mutableSetOf<String>().also { s ->
            prefSet.forEach { f -> s.add(f) }
        }
    }
}
