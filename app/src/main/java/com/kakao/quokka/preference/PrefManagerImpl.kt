package com.kakao.quokka.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.kakao.quokka.constants.QkConstants
import java.io.File
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
        docs.add(url)

        setStringSet(QkConstants.Pref.FAVORITE_KEY, docs)
    }

    override fun removeDocUrl(url: String) {
        val docs = getDocList()
        docs.remove(url)

        setStringSet(QkConstants.Pref.FAVORITE_KEY, docs)
    }

    override fun clear() {
        preferences.edit().clear().apply()
    }

    private fun getDocList(): MutableSet<String> {
        val favorsSet = getStringSet(QkConstants.Pref.FAVORITE_KEY)

        return mutableSetOf<String>().also { s ->
            favorsSet.forEach { f -> s.add(f) }
        }
    }


    override fun removePref() {
        val dir = File(context.filesDir.parent + "/shared_prefs/")
        val children = dir.list()
        for (child in children) {
            context.getSharedPreferences(
                child.replace(".xml", ""),
                Context.MODE_PRIVATE
            ).edit().clear().apply()
            File(dir, child).delete()
        }
    }
}
