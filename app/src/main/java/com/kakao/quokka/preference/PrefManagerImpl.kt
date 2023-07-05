package com.kakao.quokka.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.kakao.quokka.constants.QkConstants
import com.kakao.quokka.ext.currMillis
import com.kakao.quokka.ext.splitUrlKey
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

    override fun addDocUrl(url: String) {
        val docs = getDocList()
        docs.add("${url}||${currMillis}")

        setStringSet(QkConstants.Pref.FAVORITE_KEY, docs)
    }

    override fun removeDocUrl(url: String) {
        val docs = getDocList()

        var removeTarget = ""
        docs.forEach { d ->
            try {
                val keySet = d.splitUrlKey()
                val dUrl = keySet.first

                if (dUrl == url) {
                    removeTarget = d
                    return@forEach
                }
            } catch (e: IndexOutOfBoundsException) {
                return@forEach
            }
        }

        if (removeTarget.isNotBlank()) docs.remove(removeTarget)
        setStringSet(QkConstants.Pref.FAVORITE_KEY, docs)
    }

    private fun getDocList(): MutableSet<String> {
        val favorsSet = getStringSet(QkConstants.Pref.FAVORITE_KEY)

        return mutableSetOf<String>().also { s ->
            favorsSet.forEach { f -> s.add(f) }
        }
    }
}
