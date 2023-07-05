package com.kakao.quokka.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import java.io.File
import javax.inject.Inject


class QkPreferenceImpl @Inject constructor(
    private val context: Context,
) : QkPreference {

    override val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(context)
//    override val preferences: SharedPreferences =
//        PreferenceManager.getDefaultSharedPreferences(context)

    override fun getFileUrl(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    override fun getAllFiles(): Map<String, String> {
        val files = preferences.all

        val res = mutableMapOf<String, String>().also { _map ->
            files.forEach { _file ->
                _map[_file.key] = _file.value.toString()
            }
        }

        return res
    }

    override fun setFileUrl(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    override fun getFileKey(key: String): Boolean {
        return preferences.all.containsKey(key)
    }

    override fun delFileKey(key: String) {
        preferences.edit().remove(key).apply()
    }

    override fun clear() {
        preferences.edit().clear().apply()
    }

//    override fun setInt(key: String, value: Int) {
//        preferences.edit().putInt(key, value).apply()
//    }
//
//    override fun getInt(key: String, defaultValue: Int): Int {
//        return preferences.getInt(key, defaultValue)
//    }
//
//    override fun setLong(key: String, value: Long) {
//        preferences.edit().putLong(key, value).apply()
//    }
//
//    override fun getLong(key: String, defaultValue: Long): Long {
//        return preferences.getLong(key, defaultValue)
//    }
//
    override fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }
//
//    override fun getString(key: String, defaultValue: String): String? {
//        return preferences.getString(key, defaultValue)
//    }
//
//    override fun setBoolean(key: String, value: Boolean) {
//        preferences.edit().putBoolean(key, value).apply()
//    }
//
//    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
//        return preferences.getBoolean(key, defaultValue)
//    }
    override fun setStringSet(key: String, value: Set<String>) {
        preferences.edit().putStringSet(key, value).apply()
    }

    override fun getStringSet(key: String): Set<String> {
        return preferences.getStringSet(key, setOf()) as Set<String>
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
