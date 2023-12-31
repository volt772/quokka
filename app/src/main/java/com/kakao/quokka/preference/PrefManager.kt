package com.kakao.quokka.preference

import android.content.SharedPreferences

/**
 * PrefManager
 */
interface PrefManager {

    val preferences: SharedPreferences

    fun setStringSet(key: String, value: Set<String>)

    fun getStringSet(key: String): Set<String>

    fun updateStringSet(key: String, value: String)

    fun addStringSet(key: String, value: String)

    fun removeStringSet(key: String, value: String)

    fun clearKey(key: String)

}
